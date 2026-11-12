# Segundo Parcial · Pruebas de Software
### Unidad 2: pruebas ágiles y DevOps · Ingeniería de Software · V semestre

| | |
|---|---|
| **Modalidad** | Individual |
| **Duración** | 120 minutos |
| **Fecha** | 12/11/2026 |
| **Entrega** | Enlace a su repositorio de GitHub, en la tarea de la plataforma |

---

## Contexto

**EstacionaYa** es un parqueadero que necesita un sistema para calcular lo que paga cada vehículo y para aplicar descuentos a sus clientes. Una parte del código ya existe. Su trabajo es probarlo, completar lo que falta con TDD y BDD, limpiar el código que quedó mal escrito y dejar el pipeline de CI/CD en verde.

El parcial es **de código**: se califica lo que quede en su repositorio (código, pruebas, historial de commits y pipeline). No hay que entregar documentos.

## Reglas de negocio

### Tarifa (ya está programada)

| Regla | Detalle |
|---|---|
| Tipos de vehículo | Solo `carro` y `moto`. Cualquier otro tipo es un error: `Tipo de vehículo no válido` |
| Duración válida | Un número entero de minutos entre 0 y 1440 (24 horas), ambos incluidos. Fuera de ese rango es un error: `Los minutos deben estar entre 0 y 1440` |
| Minutos de gracia | Si la estadía dura 15 minutos o menos, no se paga nada |
| Cobro por hora o fracción | Después de la gracia se cobra cada hora o fracción de hora: **carro 3000** pesos por hora, **moto 1500** pesos por hora. Por ejemplo, 61 minutos se cobran como 2 horas |
| Tope diario | Una estadía nunca paga más del tope: **carro 25000**, **moto 12000** |

### Descuentos (no existe: lo construyen ustedes con TDD)

| Tipo de cliente | Descuento |
|---|---|
| `ninguno` | 0 % |
| `frecuente` | 10 % |
| `convenio` | 20 % |

- El resultado se redondea **hacia abajo** al peso entero.
- Un tipo de cliente distinto es un error: `Tipo de cliente no válido`.
- Un valor negativo es un error: `El valor no puede ser negativo`.

| Valor | Cliente | Resultado esperado |
|---|---|---|
| 3000 | frecuente | 2700 |
| 25000 | convenio | 20000 |
| 1999 | frecuente | 1799 (1799,1 redondeado hacia abajo) |

### Reporte del día (existe, pero está mal escrito)

`resumen` recibe la lista de estadías del día y devuelve cuántos carros y cuántas motos entraron y el total recaudado. Funciona, pero tiene problemas de calidad que el análisis estático y una revisión de Clean Code deben encontrar. Todos los registros que recibe tienen un tipo de vehículo válido.

---

## Antes de empezar (10 minutos)

1. Elijan **un** lenguaje: Python, Node.js, PHP o Java. Las instrucciones de cada uno están en los anexos al final.
2. Creen en GitHub un repositorio **público y vacío** llamado `parcial2-pruebas-<su-apellido>`.
3. En su equipo, creen la carpeta del proyecto con **exactamente** la estructura y los archivos que indica el anexo de su lenguaje. Copien cada archivo tal cual.
4. Instalen las dependencias con el comando del anexo y comprueben que las pruebas iniciales pasan.
5. Hagan el primer commit y súbanlo:
   ```bash
   git init -b main
   git add .
   git commit -m "chore: estructura inicial del parcial"
   git remote add origin https://github.com/<su-usuario>/parcial2-pruebas-<su-apellido>.git
   git push -u origin main
   ```
6. Abran la pestaña **Actions** del repositorio. **El pipeline va a quedar en rojo, y es lo esperado**: faltan pruebas, hay hallazgos de análisis estático y la cobertura no alcanza. Su trabajo es dejarlo en verde.

El pipeline lo entrega el docente completo, dentro del anexo de cada lenguaje. **No lo modifiquen.**

---

## Lo que deben hacer

### Punto 1 · Pruebas unitarias de la tarifa (20 %)

Amplíen las pruebas unitarias de la función que calcula la tarifa. Con base en las reglas de negocio, identifiquen ustedes las **particiones de equivalencia** (grupos de entradas que el sistema trata igual) y los **valores límite** de cada regla, y escriban las pruebas que los cubran.

- Mínimo **10 casos nuevos**, además de los que ya existen.
- Deben cubrir **todos los límites** de cada regla (la gracia, el cambio de hora, el tope de cada vehículo y el rango válido de minutos) y **todas las entradas inválidas**.
- Cada prueba verifica un solo comportamiento y su nombre dice qué se espera.
- Donde varias pruebas solo cambian los datos, usen pruebas parametrizadas.

```bash
git commit -m "test: casos de partición y valores límite de la tarifa"
```

### Punto 2 · Descuentos con TDD (25 %)

Construyan la función de descuentos **con TDD**, siguiendo las reglas de la sección anterior. El nombre de la función y de los archivos está en el anexo de su lenguaje.

El historial de commits **debe** mostrar el ciclo, en este orden:

1. **Rojo:** escriban una sola prueba, córranla y comprueben que falla porque la función no existe.
   ```bash
   git commit -m "test: prueba en rojo de aplicar descuento"
   ```
2. **Verde:** escriban el código mínimo para que pase.
   ```bash
   git commit -m "feat: aplicar descuento"
   ```
3. Repitan rojo y verde para el resto de los casos: los tres tipos de cliente, el redondeo, el valor 0, el valor negativo y el tipo inválido.
4. **Refactor:** mejoren el código sin cambiar el comportamiento, con todas las pruebas en verde.
   ```bash
   git commit -m "refactor: aplicar descuento"
   ```

Un historial con todo en un solo commit no demuestra TDD y no recibe el puntaje del ciclo.

### Punto 3 · Escenarios BDD (20 %)

Escriban un archivo `.feature` en **español** (la primera línea debe ser `# language: es`) con sus pasos implementados y corriendo:

- Al menos un **`Esquema del escenario`** con tabla de **`Ejemplos`** que cubra la gracia, el cobro por fracción y el tope diario de los dos tipos de vehículo.
- Al menos un **`Escenario`** de descuentos.
- Redáctenlos para que el dueño del parqueadero los pueda leer sin saber programar.

```bash
git commit -m "test: escenarios BDD de tarifas y descuentos"
```

### Punto 4 · Análisis estático y Clean Code (20 %)

1. Corran el analizador estático de su lenguaje. Va a reportar hallazgos en el archivo de reportes.
2. Corrijan **todos** los hallazgos.
3. Refactoricen `resumen` aplicando Clean Code: nombres que expliquen, sin números repetidos y **reutilizando la función de tarifa** en lugar de duplicar sus reglas.
4. Las pruebas de reportes que vienen en el proyecto deben seguir pasando **sin modificarlas**: son las que demuestran que el refactor no cambió el comportamiento.
5. Al final, el analizador no debe reportar nada en todo el proyecto, incluidas sus pruebas.

```bash
git commit -m "refactor: resumen sin hallazgos de análisis estático"
```

### Punto 5 · Cobertura y pipeline en verde (15 %)

- La cobertura debe alcanzar el mínimo que exige el pipeline de su lenguaje (90 %; en PHP, 95 % de líneas).
- El último pipeline de la pestaña **Actions** debe estar completamente en verde, y el job de despliegue debe imprimir `Desplegado`.

```bash
git push
```

---

## Reglas del parcial

- Es individual.
- No modifiquen el pipeline, los umbrales de cobertura ni las reglas del analizador estático. No desactiven reglas en el código para ocultar hallazgos.
- No modifiquen las pruebas de reportes que vienen en el proyecto.
- Hagan commits a medida que avanzan, con los mensajes indicados. El historial es parte de la evidencia.
- Lo que no esté subido a GitHub a la hora de cierre no se califica.

## Entrega

En la tarea de la plataforma, solo el enlace al repositorio: `https://github.com/<su-usuario>/parcial2-pruebas-<su-apellido>`

## Rúbrica

| Punto | Qué se revisa | Peso |
|---|---|---|
| 1 · Pruebas unitarias | Particiones y límites completos, una verificación por prueba, nombres claros, parametrización | 20 % |
| 2 · TDD | Función correcta según las reglas y commits de rojo, verde y refactor en orden | 25 % |
| 3 · BDD | Escenarios en español legibles para el negocio, Esquema con Ejemplos y pasos que los ejecutan | 20 % |
| 4 · Análisis estático y Clean Code | Cero hallazgos, `resumen` reutiliza la tarifa y sus pruebas siguen intactas y en verde | 20 % |
| 5 · Cobertura y pipeline | Cobertura mínima alcanzada y pipeline completo en verde | 15 % |

---
## Anexo A · Python (uv, pytest, pytest-bdd, Ruff)

**Requisitos:** [uv](https://docs.astral.sh/uv/getting-started/installation/). Si no tienen la versión de Python que pide el proyecto, uv la descarga.

### Estructura

```
parcial2-pruebas-<su-apellido>/
├── .github/
│   └── workflows/
│       └── ci-cd.yml
├── src/
│   └── parqueadero/
│       ├── __init__.py
│       ├── tarifas.py
│       └── reportes.py
├── tests/
│   ├── unit/
│   │   ├── test_tarifas.py
│   │   └── test_reportes.py
│   └── bdd/
│       └── features/
├── .gitignore
└── pyproject.toml
```

### Archivos

**`pyproject.toml`**

```toml
[project]
name = "parqueadero"
version = "1.0.0"
description = "Segundo parcial de Pruebas de Software: tarifas de un parqueadero."
requires-python = ">=3.12"
dependencies = []

[dependency-groups]
dev = [
    "pytest>=9.2.0",
    "pytest-bdd>=9.0.1",
    "pytest-cov>=7.1.0",
    "ruff>=0.17.4",
]

[build-system]
requires = ["uv_build>=0.11.7,<0.12.0"]
build-backend = "uv_build"

# --import-mode=importlib permite archivos de prueba con el mismo nombre en carpetas distintas.
[tool.pytest.ini_options]
testpaths = ["tests"]
addopts = "-ra --import-mode=importlib"

# Análisis estático: E y F (errores y defectos probables), I (orden de importaciones)
# y C90 (complejidad ciclomática, máximo 5 por función).
[tool.ruff]
line-length = 100

[tool.ruff.lint]
select = ["E", "F", "I", "C90"]

[tool.ruff.lint.mccabe]
max-complexity = 5

# Cobertura de líneas y de ramas del código fuente.
[tool.coverage.run]
source = ["src/parqueadero"]
branch = true
```
**`.gitignore`**

```text
.venv/
__pycache__/
*.py[oc]
.pytest_cache/
.ruff_cache/
.coverage
htmlcov/
```
**`src/parqueadero/__init__.py`**

```python
"""Reglas de negocio del parqueadero EstacionaYa."""
```
**`src/parqueadero/tarifas.py`**

```python
"""Cálculo de la tarifa de un parqueadero."""

import math

MINUTOS_DE_GRACIA = 15
MINUTOS_MAXIMOS = 1440
TARIFA_POR_HORA = {"carro": 3000, "moto": 1500}
TOPE_DIARIO = {"carro": 25000, "moto": 12000}


def calcular_tarifa(minutos: int, tipo_vehiculo: str) -> int:
    """Calcula el valor a pagar, en pesos, por una estadía en el parqueadero.

    Los primeros 15 minutos son gratis. Después se cobra cada hora o fracción
    de hora según el tipo de vehículo, sin superar el tope diario.
    """
    if tipo_vehiculo not in TARIFA_POR_HORA:
        raise ValueError("Tipo de vehículo no válido")
    if not 0 <= minutos <= MINUTOS_MAXIMOS:
        raise ValueError("Los minutos deben estar entre 0 y 1440")

    if minutos <= MINUTOS_DE_GRACIA:
        return 0

    horas_cobradas = math.ceil(minutos / 60)
    valor = horas_cobradas * TARIFA_POR_HORA[tipo_vehiculo]
    return min(valor, TOPE_DIARIO[tipo_vehiculo])
```
**`src/parqueadero/reportes.py`**

```python
import math
import os


def resumen(registros):
    r = {"carro": 0, "moto": 0, "total_recaudado": 0}
    temporal = 0
    for reg in registros:
        t = reg[0]
        m = reg[1]
        if t == "carro":
            r["carro"] += 1
            if m > 15:
                v = math.ceil(m / 60) * 3000
                if v > 25000:
                    v = 25000
                r["total_recaudado"] += v
        elif t == "moto":
            r["moto"] += 1
            if m > 15:
                v = math.ceil(m / 60) * 1500
                if v > 12000:
                    v = 12000
                r["total_recaudado"] += v
    return r
```
**`tests/unit/test_tarifas.py`**

```python
"""Pruebas unitarias iniciales de calcular_tarifa. Están incompletas a propósito."""

import pytest

from parqueadero.tarifas import calcular_tarifa


def test_carro_con_una_hora_y_un_minuto_paga_dos_horas():
    assert calcular_tarifa(61, "carro") == 6000


def test_moto_con_media_hora_paga_una_hora():
    assert calcular_tarifa(30, "moto") == 1500


def test_rechaza_un_tipo_de_vehiculo_desconocido():
    with pytest.raises(ValueError, match="Tipo de vehículo no válido"):
        calcular_tarifa(30, "bicicleta")
```
**`tests/unit/test_reportes.py`**

```python
"""Pruebas que protegen el comportamiento de resumen durante el refactor."""

from parqueadero.reportes import resumen


def test_resume_los_registros_del_dia():
    registros = [("carro", 10), ("carro", 61), ("moto", 600), ("carro", 1440)]

    resultado = resumen(registros)

    # 0 (gracia) + 6000 (2 horas) + 12000 (tope moto) + 25000 (tope carro)
    assert resultado == {"carro": 3, "moto": 1, "total_recaudado": 43000}


def test_sin_registros_todo_es_cero():
    assert resumen([]) == {"carro": 0, "moto": 0, "total_recaudado": 0}
```
**`.github/workflows/ci-cd.yml`**

```yaml
# Pipeline de integración y despliegue continuos del parcial.
# Lo entrega el docente completo: no hace falta modificarlo.
# Cada job corre en su propia máquina virtual (runner). El despliegue,
# que es simulado, solo corre si los cuatro jobs de verificación pasan.

name: CI/CD

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  analisis-estatico:
    name: Análisis estático
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: astral-sh/setup-uv@v8.1.0
        with:
          enable-cache: true
      - run: uv sync --locked
      - name: Análisis estático
        run: uv run ruff check . && uv run ruff format --check .

  pruebas-unitarias:
    name: Pruebas unitarias
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: astral-sh/setup-uv@v8.1.0
        with:
          enable-cache: true
      - run: uv sync --locked
      - name: Pruebas unitarias
        run: uv run pytest tests/unit -v

  pruebas-bdd:
    name: Pruebas BDD
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: astral-sh/setup-uv@v8.1.0
        with:
          enable-cache: true
      - run: uv sync --locked
      - name: Pruebas BDD
        run: uv run pytest tests/bdd -v

  cobertura:
    name: Cobertura
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: astral-sh/setup-uv@v8.1.0
        with:
          enable-cache: true
      - run: uv sync --locked
      - name: Cobertura
        run: uv run pytest tests/unit tests/bdd --cov --cov-report=term-missing --cov-fail-under=90


  despliegue:
    name: Despliegue (simulado)
    needs: [analisis-estatico, pruebas-unitarias, pruebas-bdd, cobertura]
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - name: Desplegar
        run: |
          echo "Versión: ${{ github.sha }}"
          echo "Desplegado"
```
### Comandos

```bash
uv sync                                   # instala todo y crea uv.lock (súbanlo al repositorio)
uv run pytest tests/unit -v               # pruebas unitarias
uv run pytest tests/bdd -v                # escenarios BDD
uv run ruff check .                       # análisis estático
uv run ruff format .                      # formatea el código
uv run pytest tests/unit tests/bdd --cov --cov-report=term-missing   # cobertura
```

`uv.lock` debe ir en el primer commit: el pipeline instala con `uv sync --locked` y falla si no existe.

### Dónde va lo que construyen

| Punto | Archivos |
|---|---|
| 2 · TDD | `src/parqueadero/descuentos.py` con `aplicar_descuento(valor, tipo_cliente)`, y `tests/unit/test_descuentos.py` |
| 3 · BDD | `tests/bdd/features/tarifas.feature` y `tests/bdd/test_tarifas_bdd.py` (con `scenarios("features/tarifas.feature")` de pytest-bdd) |

Documentación: [pytest](https://docs.pytest.org/) · [pytest-bdd](https://pytest-bdd.readthedocs.io/) · [Ruff](https://docs.astral.sh/ruff/rules/) · [pytest-cov](https://pytest-cov.readthedocs.io/)

---
## Anexo B · Node.js (Jest, jest-cucumber, ESLint)

**Requisitos:** [Node.js](https://nodejs.org/) 24 LTS (mínimo 22.13).

### Estructura

```
parcial2-pruebas-<su-apellido>/
├── .github/
│   └── workflows/
│       └── ci-cd.yml
├── src/
│   ├── tarifas.js
│   └── reportes.js
├── tests/
│   ├── unit/
│   │   ├── tarifas.test.js
│   │   └── reportes.test.js
│   └── bdd/
│       └── features/
├── .gitignore
├── eslint.config.mjs
├── jest.config.js
└── package.json
```

### Archivos

**`package.json`**

```json
{
  "name": "parcial-parqueadero",
  "version": "1.0.0",
  "description": "Segundo parcial de Pruebas de Software: tarifas de un parqueadero.",
  "private": true,
  "scripts": {
    "lint": "eslint .",
    "test:unit": "jest tests/unit",
    "test:bdd": "jest tests/bdd",
    "test:cobertura": "jest --coverage"
  },
  "devDependencies": {
    "@eslint/js": "^10.0.1",
    "eslint": "^10.14.0",
    "globals": "^17.14.0",
    "jest": "^30.5.2",
    "jest-cucumber": "^4.5.0"
  }
}
```
**`jest.config.js`**

```javascript
/**
 * Configuración de Jest.
 * testMatch: los *.test.js son pruebas unitarias y los *.steps.js conectan los escenarios BDD.
 * coverageThreshold: si la cobertura de líneas o de ramas baja de 90 %, Jest termina con error.
 */
module.exports = {
  testEnvironment: 'node',
  testMatch: ['**/tests/**/*.test.js', '**/tests/**/*.steps.js'],
  collectCoverageFrom: ['src/**/*.js'],
  coverageThreshold: {
    global: { lines: 90, branches: 90 },
  },
};
```
**`eslint.config.mjs`**

```javascript
/**
 * Configuración de ESLint (formato "flat config", el único vigente desde ESLint 10).
 * Usa extensión .mjs porque el proyecto es CommonJS y este archivo usa import/export.
 */
import js from '@eslint/js';
import globals from 'globals';

export default [
  { ignores: ['node_modules/', 'coverage/'] },
  js.configs.recommended,
  {
    files: ['**/*.js'],
    languageOptions: { sourceType: 'commonjs', globals: { ...globals.node } },
    rules: {
      eqeqeq: 'error',
      complexity: ['error', 5],
    },
  },
  {
    files: ['tests/**/*.js'],
    languageOptions: { globals: { ...globals.jest } },
  },
];
```
**`.gitignore`**

```text
node_modules/
coverage/
```
**`src/tarifas.js`**

```javascript
/** Cálculo de la tarifa de un parqueadero. */

const MINUTOS_DE_GRACIA = 15;
const MINUTOS_MAXIMOS = 1440;
const TARIFA_POR_HORA = { carro: 3000, moto: 1500 };
const TOPE_DIARIO = { carro: 25000, moto: 12000 };

/**
 * Calcula el valor a pagar, en pesos, por una estadía en el parqueadero.
 * Los primeros 15 minutos son gratis. Después se cobra cada hora o fracción
 * de hora según el tipo de vehículo, sin superar el tope diario.
 */
function calcularTarifa(minutos, tipoVehiculo) {
  validarEntrada(minutos, tipoVehiculo);

  if (minutos <= MINUTOS_DE_GRACIA) {
    return 0;
  }

  const horasCobradas = Math.ceil(minutos / 60);
  const valor = horasCobradas * TARIFA_POR_HORA[tipoVehiculo];
  return Math.min(valor, TOPE_DIARIO[tipoVehiculo]);
}

function validarEntrada(minutos, tipoVehiculo) {
  if (!Object.hasOwn(TARIFA_POR_HORA, tipoVehiculo)) {
    throw new Error('Tipo de vehículo no válido');
  }
  if (!esDuracionValida(minutos)) {
    throw new Error('Los minutos deben estar entre 0 y 1440');
  }
}

function esDuracionValida(minutos) {
  return Number.isInteger(minutos) && minutos >= 0 && minutos <= MINUTOS_MAXIMOS;
}

module.exports = { calcularTarifa };
```
**`src/reportes.js`**

```javascript
const fs = require('fs');

function resumen(registros) {
  const r = { carro: 0, moto: 0, totalRecaudado: 0 };
  let temporal = 0;
  for (const reg of registros) {
    if (reg.tipo == 'carro') {
      r.carro++;
      if (reg.minutos > 15) {
        let v = Math.ceil(reg.minutos / 60) * 3000;
        if (v > 25000) {
          v = 25000;
        }
        r.totalRecaudado += v;
      }
    } else if (reg.tipo == 'moto') {
      r.moto++;
      if (reg.minutos > 15) {
        let v = Math.ceil(reg.minutos / 60) * 1500;
        if (v > 12000) {
          v = 12000;
        }
        r.totalRecaudado += v;
      }
    }
  }
  return r;
}

module.exports = { resumen };
```
**`tests/unit/tarifas.test.js`**

```javascript
/** Pruebas unitarias iniciales de calcularTarifa. Están incompletas a propósito. */
const { calcularTarifa } = require('../../src/tarifas');

describe('calcularTarifa', () => {
  test('un carro con una hora y un minuto paga dos horas', () => {
    expect(calcularTarifa(61, 'carro')).toBe(6000);
  });

  test('una moto con media hora paga una hora', () => {
    expect(calcularTarifa(30, 'moto')).toBe(1500);
  });

  test('rechaza un tipo de vehículo desconocido', () => {
    expect(() => calcularTarifa(30, 'bicicleta')).toThrow('Tipo de vehículo no válido');
  });
});
```
**`tests/unit/reportes.test.js`**

```javascript
/** Pruebas que protegen el comportamiento de resumen durante el refactor. */
const { resumen } = require('../../src/reportes');

describe('resumen', () => {
  test('resume los registros del día', () => {
    const registros = [
      { tipo: 'carro', minutos: 10 },
      { tipo: 'carro', minutos: 61 },
      { tipo: 'moto', minutos: 600 },
      { tipo: 'carro', minutos: 1440 },
    ];

    // 0 (gracia) + 6000 (2 horas) + 12000 (tope moto) + 25000 (tope carro)
    expect(resumen(registros)).toEqual({ carro: 3, moto: 1, totalRecaudado: 43000 });
  });

  test('sin registros todo es cero', () => {
    expect(resumen([])).toEqual({ carro: 0, moto: 0, totalRecaudado: 0 });
  });
});
```
**`.github/workflows/ci-cd.yml`**

```yaml
# Pipeline de integración y despliegue continuos del parcial.
# Lo entrega el docente completo: no hace falta modificarlo.
# Cada job corre en su propia máquina virtual (runner). El despliegue,
# que es simulado, solo corre si los cuatro jobs de verificación pasan.

name: CI/CD

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  analisis-estatico:
    name: Análisis estático
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-node@v6
        with:
          node-version: 24
          cache: npm
      - run: npm ci
      - name: Análisis estático
        run: npm run lint

  pruebas-unitarias:
    name: Pruebas unitarias
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-node@v6
        with:
          node-version: 24
          cache: npm
      - run: npm ci
      - name: Pruebas unitarias
        run: npm run test:unit

  pruebas-bdd:
    name: Pruebas BDD
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-node@v6
        with:
          node-version: 24
          cache: npm
      - run: npm ci
      - name: Pruebas BDD
        run: npm run test:bdd

  cobertura:
    name: Cobertura
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-node@v6
        with:
          node-version: 24
          cache: npm
      - run: npm ci
      - name: Cobertura
        run: npm run test:cobertura


  despliegue:
    name: Despliegue (simulado)
    needs: [analisis-estatico, pruebas-unitarias, pruebas-bdd, cobertura]
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - name: Desplegar
        run: |
          echo "Versión: ${{ github.sha }}"
          echo "Desplegado"
```
### Comandos

```bash
npm install               # instala todo y crea package-lock.json (súbanlo al repositorio)
npm run test:unit         # pruebas unitarias
npm run test:bdd          # escenarios BDD
npm run lint              # análisis estático
npm run test:cobertura    # todas las pruebas con cobertura y umbral de 90 %
```

`package-lock.json` debe ir en el primer commit: el pipeline instala con `npm ci` y falla si no existe.

### Dónde va lo que construyen

| Punto | Archivos |
|---|---|
| 2 · TDD | `src/descuentos.js` con `aplicarDescuento(valor, tipoCliente)`, exportada con `module.exports`, y `tests/unit/descuentos.test.js` |
| 3 · BDD | `tests/bdd/features/tarifas.feature` y `tests/bdd/tarifas.steps.js` (con `loadFeature` y `defineFeature` de jest-cucumber) |

Documentación: [Jest](https://jestjs.io/docs/getting-started) · [jest-cucumber](https://github.com/bencompton/jest-cucumber) · [ESLint](https://eslint.org/docs/latest/rules/)

---
## Anexo C · PHP (PHPUnit, Behat, PHPStan)

**Requisitos:** [PHP](https://www.php.net/downloads) 8.3 o superior y [Composer](https://getcomposer.org/download/). Para medir la cobertura en su equipo necesitan además PCOV o Xdebug; si no los tienen, la cobertura se mide en el pipeline.

### Estructura

```
parcial2-pruebas-<su-apellido>/
├── .github/
│   └── workflows/
│       └── ci-cd.yml
├── bin/
│   └── verificar-cobertura.php
├── features/
├── src/
│   ├── CalculadoraTarifas.php
│   └── Reportes.php
├── tests/
│   └── Unit/
│       ├── CalculadoraTarifasTest.php
│       └── ReportesTest.php
├── .gitignore
├── composer.json
├── phpstan.neon
└── phpunit.xml
```

### Archivos

**`composer.json`**

```json
{
    "name": "curso-pruebas/parcial-parqueadero",
    "description": "Segundo parcial de Pruebas de Software: tarifas de un parqueadero.",
    "type": "project",
    "license": "proprietary",
    "require": {
        "php": ">=8.3"
    },
    "require-dev": {
        "behat/behat": "^3.14",
        "phpstan/phpstan": "^2.1",
        "phpunit/phpunit": "^12.0"
    },
    "autoload": {
        "psr-4": {
            "Parqueadero\\": "src/"
        }
    },
    "autoload-dev": {
        "psr-4": {
            "Parqueadero\\Tests\\": "tests/"
        }
    },
    "scripts": {
        "lint": "phpstan analyse",
        "test:unit": "phpunit --testsuite unit",
        "test:bdd": "behat",
        "test:cobertura": "phpunit --coverage-text --coverage-clover coverage.xml"
    }
}
```
**`phpunit.xml`**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  bootstrap carga el autoloader de Composer; testsuites agrupa las pruebas;
  source indica qué código se mide al pedir cobertura.
-->
<phpunit xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="vendor/phpunit/phpunit/phpunit.xsd"
         bootstrap="vendor/autoload.php"
         colors="true"
         failOnWarning="true">
    <testsuites>
        <testsuite name="unit">
            <directory>tests/Unit</directory>
        </testsuite>
    </testsuites>
    <source>
        <include>
            <directory>src</directory>
        </include>
    </source>
</phpunit>
```
**`phpstan.neon`**

```yaml
# Análisis estático. En el nivel 6 PHPStan exige tipos en parámetros y retornos,
# y que los arreglos documenten qué contienen (por ejemplo list<array{tipo: string, minutos: int}>).
parameters:
    level: 6
    paths:
        - src
        - tests
```
**`.gitignore`**

```text
vendor/
coverage.xml
.phpunit.cache/
```
**`bin/verificar-cobertura.php`**

```php
<?php

declare(strict_types=1);

/**
 * PHPUnit genera el reporte de cobertura, pero no falla si el porcentaje es bajo.
 * Este script lee el reporte Clover y termina con error si no se alcanza el mínimo.
 * Uso: php bin/verificar-cobertura.php coverage.xml 90
 */

$archivo = $argv[1] ?? 'coverage.xml';
$minimo = (float) ($argv[2] ?? 90);

if (!is_file($archivo)) {
    fwrite(STDERR, "No se encontró el reporte {$archivo}. Ejecuten primero: composer test:cobertura\n");
    exit(1);
}

$reporte = simplexml_load_file($archivo);
if ($reporte === false) {
    fwrite(STDERR, "El archivo {$archivo} no es un reporte Clover válido.\n");
    exit(1);
}

$metricas = $reporte->project->metrics;
$sentencias = (int) $metricas['statements'];
$cubiertas = (int) $metricas['coveredstatements'];
$porcentaje = $sentencias === 0 ? 100.0 : $cubiertas / $sentencias * 100;

printf("Cobertura de líneas: %.2f %% (mínimo exigido: %.0f %%)\n", $porcentaje, $minimo);

if ($porcentaje < $minimo) {
    fwrite(STDERR, "La cobertura está por debajo del mínimo.\n");
    exit(1);
}
```
**`src/CalculadoraTarifas.php`**

```php
<?php

declare(strict_types=1);

namespace Parqueadero;

use InvalidArgumentException;

/** Cálculo de la tarifa de un parqueadero. */
final class CalculadoraTarifas
{
    private const MINUTOS_DE_GRACIA = 15;
    private const MINUTOS_MAXIMOS = 1440;
    private const TARIFA_POR_HORA = ['carro' => 3000, 'moto' => 1500];
    private const TOPE_DIARIO = ['carro' => 25000, 'moto' => 12000];

    /**
     * Calcula el valor a pagar, en pesos, por una estadía en el parqueadero.
     * Los primeros 15 minutos son gratis. Después se cobra cada hora o fracción
     * de hora según el tipo de vehículo, sin superar el tope diario.
     */
    public function calcularTarifa(int $minutos, string $tipoVehiculo): int
    {
        if (!array_key_exists($tipoVehiculo, self::TARIFA_POR_HORA)) {
            throw new InvalidArgumentException('Tipo de vehículo no válido');
        }
        if ($minutos < 0 || $minutos > self::MINUTOS_MAXIMOS) {
            throw new InvalidArgumentException('Los minutos deben estar entre 0 y 1440');
        }

        if ($minutos <= self::MINUTOS_DE_GRACIA) {
            return 0;
        }

        $horasCobradas = (int) ceil($minutos / 60);
        $valor = $horasCobradas * self::TARIFA_POR_HORA[$tipoVehiculo];

        return min($valor, self::TOPE_DIARIO[$tipoVehiculo]);
    }
}
```
**`src/Reportes.php`**

```php
<?php

declare(strict_types=1);

namespace Parqueadero;

final class Reportes
{
    public function resumen($registros)
    {
        $r = ['carro' => 0, 'moto' => 0, 'total_recaudado' => 0];
        $temporal = 0;
        foreach ($registros as $reg) {
            if ($reg['tipo'] == 'carro') {
                $r['carro']++;
                if ($reg['minutos'] > 15) {
                    $v = (int) ceil($reg['minutos'] / 60) * 3000;
                    if ($v > 25000) {
                        $v = 25000;
                    }
                    $r['total_recaudado'] += $v;
                }
            } elseif ($reg['tipo'] == 'moto') {
                $r['moto']++;
                if ($reg['minutos'] > 15) {
                    $v = (int) ceil($reg['minutos'] / 60) * 1500;
                    if ($v > 12000) {
                        $v = 12000;
                    }
                    $r['total_recaudado'] += $v;
                }
            }
        }
        return $r;
    }
}
```
**`tests/Unit/CalculadoraTarifasTest.php`**

```php
<?php

declare(strict_types=1);

namespace Parqueadero\Tests\Unit;

use InvalidArgumentException;
use Parqueadero\CalculadoraTarifas;
use PHPUnit\Framework\TestCase;

/** Pruebas unitarias iniciales de calcularTarifa. Están incompletas a propósito. */
final class CalculadoraTarifasTest extends TestCase
{
    private CalculadoraTarifas $calculadora;

    protected function setUp(): void
    {
        $this->calculadora = new CalculadoraTarifas();
    }

    public function testUnCarroConUnaHoraYUnMinutoPagaDosHoras(): void
    {
        $this->assertSame(6000, $this->calculadora->calcularTarifa(61, 'carro'));
    }

    public function testUnaMotoConMediaHoraPagaUnaHora(): void
    {
        $this->assertSame(1500, $this->calculadora->calcularTarifa(30, 'moto'));
    }

    public function testRechazaUnTipoDeVehiculoDesconocido(): void
    {
        $this->expectException(InvalidArgumentException::class);
        $this->expectExceptionMessage('Tipo de vehículo no válido');

        $this->calculadora->calcularTarifa(30, 'bicicleta');
    }
}
```
**`tests/Unit/ReportesTest.php`**

```php
<?php

declare(strict_types=1);

namespace Parqueadero\Tests\Unit;

use Parqueadero\Reportes;
use PHPUnit\Framework\TestCase;

/** Pruebas que protegen el comportamiento de resumen durante el refactor. */
final class ReportesTest extends TestCase
{
    public function testResumeLosRegistrosDelDia(): void
    {
        $registros = [
            ['tipo' => 'carro', 'minutos' => 10],
            ['tipo' => 'carro', 'minutos' => 61],
            ['tipo' => 'moto', 'minutos' => 600],
            ['tipo' => 'carro', 'minutos' => 1440],
        ];

        // 0 (gracia) + 6000 (2 horas) + 12000 (tope moto) + 25000 (tope carro)
        $this->assertSame(
            ['carro' => 3, 'moto' => 1, 'total_recaudado' => 43000],
            (new Reportes())->resumen($registros),
        );
    }

    public function testSinRegistrosTodoEsCero(): void
    {
        $this->assertSame(
            ['carro' => 0, 'moto' => 0, 'total_recaudado' => 0],
            (new Reportes())->resumen([]),
        );
    }
}
```
**`.github/workflows/ci-cd.yml`**

```yaml
# Pipeline de integración y despliegue continuos del parcial.
# Lo entrega el docente completo: no hace falta modificarlo.
# Cada job corre en su propia máquina virtual (runner). El despliegue,
# que es simulado, solo corre si los cuatro jobs de verificación pasan.

name: CI/CD

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  analisis-estatico:
    name: Análisis estático
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: shivammathur/setup-php@v2
        with:
          php-version: '8.4'
          coverage: none
      - run: composer install --no-interaction --no-progress
      - name: Análisis estático
        run: composer lint

  pruebas-unitarias:
    name: Pruebas unitarias
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: shivammathur/setup-php@v2
        with:
          php-version: '8.4'
          coverage: none
      - run: composer install --no-interaction --no-progress
      - name: Pruebas unitarias
        run: composer test:unit

  pruebas-bdd:
    name: Pruebas BDD
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: shivammathur/setup-php@v2
        with:
          php-version: '8.4'
          coverage: none
      - run: composer install --no-interaction --no-progress
      - name: Pruebas BDD
        run: composer test:bdd

  cobertura:
    name: Cobertura
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: shivammathur/setup-php@v2
        with:
          php-version: '8.4'
          coverage: pcov
      - run: composer install --no-interaction --no-progress
      - name: Cobertura
        run: composer test:cobertura && php bin/verificar-cobertura.php coverage.xml 95


  despliegue:
    name: Despliegue (simulado)
    needs: [analisis-estatico, pruebas-unitarias, pruebas-bdd, cobertura]
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - name: Desplegar
        run: |
          echo "Versión: ${{ github.sha }}"
          echo "Desplegado"
```
### Comandos

```bash
composer install                                  # instala todo y crea composer.lock (súbanlo al repositorio)
composer test:unit                                # pruebas unitarias
composer test:bdd                                 # escenarios BDD
composer lint                                     # análisis estático
composer test:cobertura                           # cobertura (necesita PCOV o Xdebug)
php bin/verificar-cobertura.php coverage.xml 95   # falla si la cobertura de líneas es menor a 95 %
```

PHPUnit mide solo cobertura de líneas, no de ramas; por eso en PHP el mínimo es 95 %.

### Dónde va lo que construyen

| Punto | Archivos |
|---|---|
| 2 · TDD | `src/Descuentos.php` con la clase `Descuentos` y el método `aplicarDescuento(int $valor, string $tipoCliente): int`, y `tests/Unit/DescuentosTest.php` |
| 3 · BDD | `features/tarifas.feature` y `features/bootstrap/FeatureContext.php`, que es donde Behat busca los pasos sin configuración adicional |

Documentación: [PHPUnit](https://docs.phpunit.de/) · [Behat](https://docs.behat.org/) · [PHPStan](https://phpstan.org/user-guide/rule-levels)

---
## Anexo D · Java (JUnit, Cucumber, Checkstyle, JaCoCo)

**Requisitos:** Java 21 y [Maven](https://maven.apache.org/download.cgi) 3.9 o superior. La primera ejecución descarga dependencias y tarda unos minutos.

### Estructura

```
parcial2-pruebas-<su-apellido>/
├── .github/
│   └── workflows/
│       └── ci-cd.yml
├── config/
│   └── checkstyle.xml
├── src/
│   ├── main/java/parqueadero/
│   │   ├── CalculadoraTarifas.java
│   │   ├── Registro.java
│   │   └── Reportes.java
│   └── test/
│       ├── java/parqueadero/
│       │   ├── unitarias/
│       │   │   ├── CalculadoraTarifasTest.java
│       │   │   └── ReportesTest.java
│       │   └── bdd/
│       │       └── RunCucumberTest.java
│       └── resources/features/
├── .gitignore
└── pom.xml
```

### Archivos

**`pom.xml`**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!--
      El proyecto no usa Spring. El parent de Spring Boot se usa solo porque fija versiones
      compatibles de JUnit y de los plugins de Maven (compilador, Surefire), y así no hay que
      declararlas una por una.
    -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.1.1</version>
        <relativePath/>
    </parent>

    <groupId>curso.pruebas</groupId>
    <artifactId>parcial-parqueadero</artifactId>
    <version>1.0.0</version>

    <properties>
        <java.version>21</java.version>
        <cucumber.version>7.34.6</cucumber.version>
        <checkstyle.version>13.3.0</checkstyle.version>
        <jacoco.version>0.8.14</jacoco.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.cucumber</groupId>
                <artifactId>cucumber-bom</artifactId>
                <version>${cucumber.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-suite</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit-platform-engine</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Análisis estático: mvn checkstyle:check. Reglas en config/checkstyle.xml. -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.6.0</version>
                <configuration>
                    <configLocation>config/checkstyle.xml</configLocation>
                    <includeTestSourceDirectory>true</includeTestSourceDirectory>
                    <consoleOutput>true</consoleOutput>
                    <failOnViolation>true</failOnViolation>
                    <violationSeverity>warning</violationSeverity>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>com.puppycrawl.tools</groupId>
                        <artifactId>checkstyle</artifactId>
                        <version>${checkstyle.version}</version>
                    </dependency>
                </dependencies>
            </plugin>

            <!--
              Cobertura con JaCoCo: prepare-agent mide mientras corren las pruebas, report genera
              target/site/jacoco/index.html y check hace fallar mvn verify si las líneas o las
              ramas cubiertas bajan de 90 %.
            -->
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.version}</version>
                <executions>
                    <execution>
                        <id>preparar-agente</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>reporte</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>verificar-cobertura</id>
                        <goals>
                            <goal>check</goal>
                        </goals>
                        <configuration>
                            <rules>
                                <rule>
                                    <element>BUNDLE</element>
                                    <limits>
                                        <limit>
                                            <counter>LINE</counter>
                                            <value>COVEREDRATIO</value>
                                            <minimum>0.90</minimum>
                                        </limit>
                                        <limit>
                                            <counter>BRANCH</counter>
                                            <value>COVEREDRATIO</value>
                                            <minimum>0.90</minimum>
                                        </limit>
                                    </limits>
                                </rule>
                            </rules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
**`config/checkstyle.xml`**

```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
        "https://checkstyle.org/dtds/configuration_1_3.dtd">
<!-- Reglas de análisis estático: código sobrante, llaves, nombres y complejidad. -->
<module name="Checker">
    <property name="charset" value="UTF-8"/>
    <property name="severity" value="error"/>
    <module name="FileTabCharacter"/>
    <module name="TreeWalker">
        <module name="UnusedImports"/>
        <module name="RedundantImport"/>
        <module name="UnusedLocalVariable"/>
        <module name="EmptyBlock"/>
        <module name="AvoidStarImport"/>
        <module name="NeedBraces"/>
        <module name="MultipleVariableDeclarations"/>
        <module name="MethodName"/>
        <module name="LocalVariableName"/>
        <module name="ParameterName"/>
        <module name="ConstantName"/>
        <module name="TypeName"/>
        <module name="CyclomaticComplexity">
            <property name="max" value="5"/>
        </module>
    </module>
</module>
```
**`.gitignore`**

```text
target/
```
**`src/main/java/parqueadero/Registro.java`**

```java
package parqueadero;

/** Una estadía en el parqueadero: tipo de vehículo y minutos de permanencia. */
public record Registro(String tipo, int minutos) {
}
```
**`src/main/java/parqueadero/CalculadoraTarifas.java`**

```java
package parqueadero;

import java.util.Map;

/** Cálculo de la tarifa de un parqueadero. */
public class CalculadoraTarifas {

    private static final int MINUTOS_DE_GRACIA = 15;
    private static final int MINUTOS_MAXIMOS = 1440;
    private static final Map<String, Integer> TARIFA_POR_HORA = Map.of("carro", 3000, "moto", 1500);
    private static final Map<String, Integer> TOPE_DIARIO = Map.of("carro", 25000, "moto", 12000);

    /**
     * Calcula el valor a pagar, en pesos, por una estadía en el parqueadero.
     * Los primeros 15 minutos son gratis. Después se cobra cada hora o fracción
     * de hora según el tipo de vehículo, sin superar el tope diario.
     */
    public int calcularTarifa(int minutos, String tipoVehiculo) {
        if (!TARIFA_POR_HORA.containsKey(tipoVehiculo)) {
            throw new IllegalArgumentException("Tipo de vehículo no válido");
        }
        if (minutos < 0 || minutos > MINUTOS_MAXIMOS) {
            throw new IllegalArgumentException("Los minutos deben estar entre 0 y 1440");
        }

        if (minutos <= MINUTOS_DE_GRACIA) {
            return 0;
        }

        int horasCobradas = (minutos + 59) / 60;
        int valor = horasCobradas * TARIFA_POR_HORA.get(tipoVehiculo);
        return Math.min(valor, TOPE_DIARIO.get(tipoVehiculo));
    }
}
```
**`src/main/java/parqueadero/Reportes.java`**

```java
package parqueadero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reportes {

    static final int tarifaCarro = 3000;

    public Map<String, Integer> resumen(List<Registro> registros) {
        Map<String, Integer> r = new HashMap<>();
        r.put("carro", 0);
        r.put("moto", 0);
        r.put("total_recaudado", 0);
        int temporal = 0;
        for (Registro reg : registros) {
            if (reg.tipo().equals("carro")) {
                r.put("carro", r.get("carro") + 1);
                if (reg.minutos() > 15) {
                    int v = (int) Math.ceil(reg.minutos() / 60.0) * tarifaCarro;
                    if (v > 25000) v = 25000;
                    r.put("total_recaudado", r.get("total_recaudado") + v);
                }
            } else if (reg.tipo().equals("moto")) {
                r.put("moto", r.get("moto") + 1);
                if (reg.minutos() > 15) {
                    int v = (int) Math.ceil(reg.minutos() / 60.0) * 1500;
                    if (v > 12000) v = 12000;
                    r.put("total_recaudado", r.get("total_recaudado") + v);
                }
            }
        }
        return r;
    }
}
```
**`src/test/java/parqueadero/unitarias/CalculadoraTarifasTest.java`**

```java
package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import parqueadero.CalculadoraTarifas;

/** Pruebas unitarias iniciales de calcularTarifa. Están incompletas a propósito. */
class CalculadoraTarifasTest {

    private final CalculadoraTarifas calculadora = new CalculadoraTarifas();

    @Test
    @DisplayName("Un carro con una hora y un minuto paga dos horas")
    void carroConUnaHoraYUnMinutoPagaDosHoras() {
        assertEquals(6000, calculadora.calcularTarifa(61, "carro"));
    }

    @Test
    @DisplayName("Una moto con media hora paga una hora")
    void motoConMediaHoraPagaUnaHora() {
        assertEquals(1500, calculadora.calcularTarifa(30, "moto"));
    }

    @Test
    @DisplayName("Rechaza un tipo de vehículo desconocido")
    void rechazaUnTipoDeVehiculoDesconocido() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.calcularTarifa(30, "bicicleta"));
    }
}
```
**`src/test/java/parqueadero/unitarias/ReportesTest.java`**

```java
package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import parqueadero.Registro;
import parqueadero.Reportes;

/** Pruebas que protegen el comportamiento de resumen durante el refactor. */
class ReportesTest {

    @Test
    @DisplayName("Resume los registros del día")
    void resumeLosRegistrosDelDia() {
        List<Registro> registros = List.of(
                new Registro("carro", 10),
                new Registro("carro", 61),
                new Registro("moto", 600),
                new Registro("carro", 1440));

        // 0 (gracia) + 6000 (2 horas) + 12000 (tope moto) + 25000 (tope carro)
        assertEquals(Map.of("carro", 3, "moto", 1, "total_recaudado", 43000), new Reportes().resumen(registros));
    }

    @Test
    @DisplayName("Sin registros todo es cero")
    void sinRegistrosTodoEsCero() {
        assertEquals(Map.of("carro", 0, "moto", 0, "total_recaudado", 0), new Reportes().resumen(List.of()));
    }
}
```
**`src/test/java/parqueadero/bdd/RunCucumberTest.java`**

```java
package parqueadero.bdd;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Ejecuta con Cucumber los escenarios de src/test/resources/features.
 * Los pasos se buscan en el paquete parqueadero.bdd.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "parqueadero.bdd")
public class RunCucumberTest {
}
```
**`.github/workflows/ci-cd.yml`**

```yaml
# Pipeline de integración y despliegue continuos del parcial.
# Lo entrega el docente completo: no hace falta modificarlo.
# Cada job corre en su propia máquina virtual (runner). El despliegue,
# que es simulado, solo corre si los cuatro jobs de verificación pasan.

name: CI/CD

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  analisis-estatico:
    name: Análisis estático
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '21'
          cache: maven
      - name: Análisis estático
        run: mvn -B checkstyle:check

  pruebas-unitarias:
    name: Pruebas unitarias
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '21'
          cache: maven
      - name: Pruebas unitarias
        run: mvn -B test -Dtest='**/unitarias/*Test'

  pruebas-bdd:
    name: Pruebas BDD
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '21'
          cache: maven
      - name: Pruebas BDD
        run: mvn -B test -Dtest=RunCucumberTest

  cobertura:
    name: Cobertura
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '21'
          cache: maven
      - name: Cobertura
        run: mvn -B verify


  despliegue:
    name: Despliegue (simulado)
    needs: [analisis-estatico, pruebas-unitarias, pruebas-bdd, cobertura]
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - name: Desplegar
        run: |
          echo "Versión: ${{ github.sha }}"
          echo "Desplegado"
```
### Comandos

```bash
mvn test -Dtest='**/unitarias/*Test'   # pruebas unitarias
mvn test -Dtest=RunCucumberTest        # escenarios BDD
mvn checkstyle:check                   # análisis estático
mvn verify                             # todas las pruebas, reporte y mínimo de cobertura de 90 %
```

El reporte de cobertura queda en `target/site/jacoco/index.html`.

### Dónde va lo que construyen

| Punto | Archivos |
|---|---|
| 2 · TDD | `src/main/java/parqueadero/Descuentos.java` con el método `int aplicarDescuento(int valor, String tipoCliente)`, y `src/test/java/parqueadero/unitarias/DescuentosTest.java` |
| 3 · BDD | `src/test/resources/features/tarifas.feature` y los pasos en `src/test/java/parqueadero/bdd/TarifasSteps.java`, con las anotaciones `@Dado`, `@Cuando` y `@Entonces` de `io.cucumber.java.es`. `RunCucumberTest` ya los ejecuta |

Documentación: [JUnit](https://docs.junit.org/) · [Cucumber JVM](https://cucumber.io/docs/installation/java/) · [Checkstyle](https://checkstyle.org/checks.html) · [JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
