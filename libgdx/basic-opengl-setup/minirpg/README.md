# Mech Umbra — Mini RPG (mechumbra-samples) 🔧

A small LibGDX sample project demonstrating a minimal 3D/2D hybrid setup: simple camera, ModelBuilder-built 3D primitives, basic Scene2D HUD/popup widgets, and a tiny RPG domain model.

This project is intended as a compact example for learning LibGDX rendering, input, and Scene2D UI patterns rather than a finished game.

---

## Quick Start ▶️

Prerequisites:
- Java 8+ (project uses sourceCompatibility = 1.8)
- Use the included Gradle wrapper (`./gradlew`)

Build:

    ./gradlew build

Run (local) — preferred by IDE:
- Run `desktop/src/.../DesktopLauncher.java` from your IDE (IntelliJ / Eclipse). On macOS **you must** add JVM arg: `-XstartOnFirstThread`.

Run (Gradle):
- The `:desktop` module doesn't have the `application` plugin configured by default. To run via Gradle, add the plugin and set `mainClass` to `com.berlinbrown.mech.umbra.DesktopLauncher` in `desktop/build.gradle` (I can add this for you).

Assets:
- Assets are in the repository root `assets/`. Files are loaded via `Gdx.files.internal()` (e.g., `fonts/Roboto-Regular.ttf`). Ensure `assets/` is on the classpath when running outside the IDE.

---

## What’s inside / Architecture 🏗️

- `:desktop` — Desktop launcher module (LWJGL3 backend). Key file: `desktop/src/com/berlinbrown/mech/umbra/DesktopLauncher.java`.
- `:core` — Game logic and rendering. Key files:
  - `MechUmbraGdxRPGGame.java` — main `ApplicationListener`, game loop, creation of models, lights, camera, input handling, and stages.
  - `screens/` — Scene2D UI widgets & screens (`MainHUDScreen`, `QuitPopupWidget`, `EnableFightPopupWidget`, `PopupExitScreen`).
  - `rpg/Character.java` — tiny domain model (POJO) with `attack()`, `takeDamage()`, and `isAlive()`.

Data flow & patterns:
- `DesktopLauncher` boots an LWJGL3 `Lwjgl3Application` which constructs `MechUmbraGdxRPGGame`.
- `MechUmbraGdxRPGGame#create()` initializes 3D models (with `ModelBuilder`), `Environment` lights, `Camera`, `ModelBatch`, `Stage`/HUD, and an inline `InputProcessor`.
- Runtime state (e.g., `hero`, `enemy`, `timeElapsed`, `counter`, `lastMessage`) is exposed as `public static` fields on `MechUmbraGdxRPGGame` and read by HUD screens.

---

## Project-specific Conventions & Tips ✅

- Fonts: Created with `FreeTypeFontGenerator` (see `MainHUDScreen.createFont()`) — **always dispose** the generator after generating a font.
- Skins: UI skins are created programmatically (no JSON skin files). See `QuitPopupWidget.createBasicSkin()` / `EnableFightPopupWidget.createBasicSkin()` for the pattern using `Pixmap` + `BitmapFont` + `Texture`.
- 3D models: Built inline using `ModelBuilder`/`MeshPartBuilder`. Use `Usage.Position | Usage.Normal` when creating meshes.
- Input: An `InputProcessor` is created inline in `MechUmbraGdxRPGGame#create()`. The code currently uses numeric keycodes (e.g., `152`, `146`) — prefer replacing these with `Input.Keys.R` / `Input.Keys.F` for clarity.
- Resource disposal: Dispose of `Stage`, `Skin`, and `BitmapFont` in `dispose()` methods (see `MainHUDScreen.dispose()` and `PopupExitScreen.dispose()`).
- Global state: `hero` and `enemy` are `static` — be cautious when refactoring to avoid tight coupling.

---

## Quick Tasks / Good First Changes ✏️

- Add a Gradle `run` task to `desktop/build.gradle` using the `application` plugin so `./gradlew :desktop:run` works.
- Replace numeric keycodes with `Input.Keys` constants across the repository.
- Add a simple unit test for `Character.takeDamage()` (non-GDX logic is safe to unit test).

---

## Links & References
- LibGDX: https://libgdx.com/
- OpenGL: https://www.opengl.org/

