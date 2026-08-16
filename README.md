# Vroom-Vroom

A model-driven code generator for a public passenger transport domain, developed for the course
Rapid Software Development Methodologies (Faculty of Technical Sciences, 2025/26).

**Authors:** Nemanja Simsic (R2 18/2025), Ilija Vitosevic (E2 86/2025)

## Overview

Rather than defining a domain-specific language from scratch, the project uses UML extended with
a custom profile. Stereotypes and tagged values carry the domain semantics, while a MagicDraw
plugin reads the model and produces a Spring backend with standard CRUD layers.

```
MagicDraw model + TransportProfile
    -> ModelAnalyzer        reads UML through the MagicDraw Open API
    -> FMModel              intermediate model (FMClass, FMProperty, FMEnumeration)
    -> TransportGenerator
    -> FreeMarker templates
    -> Java / JPA / Spring source code
```

The intermediate model decouples the generator from the MagicDraw API: templates operate on
`FMClass` and `FMProperty` only, never on UML elements directly.

## Requirements

| Component | Version | Notes |
|---|---|---|
| MagicDraw | 17.0.3 | expected at `C:\Program Files\MagicDraw UML` |
| JDK | 8 | required for compilation |
| Eclipse | any recent release | used for development only |
| FreeMarker | bundled in `lib/` | template engine |
| XStream | 1.3.1, bundled in `lib/` | intermediate model serialisation |

MagicDraw runs on its own bundled JRE 1.7, so the plugin is compiled with `source`/`target` set
to 1.7. The Ant script invokes the JDK 8 compiler directly and writes to a dedicated output
directory, independent of the Eclipse build path. Installation paths are configured in
`PluginDevelopment/build.properties`.

## Build and deployment

Import `PluginDevelopment` into an Eclipse workspace as an existing project, set the paths in
`build.properties`, then run `build.xml` (default target `rebuild`). The script compiles the
sources, packages the plugin and copies it together with its libraries and templates into the
MagicDraw plugins directory. Deployment writes to the MagicDraw installation directory and
therefore requires elevated privileges.

## Usage

MagicDraw is started normally; Eclipse is used only to develop and build the plugin.

1. Open `TransportGenerator/TransportGenerator.mdzip`
2. Select `Code Generation > Generate` from the main menu

Generated sources are written to the output directory configured in `MyPlugin`.