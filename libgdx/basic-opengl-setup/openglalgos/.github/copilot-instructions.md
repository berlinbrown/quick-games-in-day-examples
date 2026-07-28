# Copilot / AI Agent Instructions for MiniRPG (MechUmbra)

Purpose: Give focused, actionable guidance so an AI coding agent becomes productive quickly in this repo.

## Quick Overview 🔧
- Multi-module LibGDX project with two main modules: `:core` (game logic & assets) and `:desktop` (desktop launcher). See `core/src/.../MechUmbraGdxRPGGame.java` and `desktop/src/.../DesktopLauncher.java`.
- The entry point for running locally from IDE: run `DesktopLauncher.main`. Note: macOS requires the JVM flag `-XstartOnFirstThread`.
- Primary rendering & game loop lives in `MechUmbraGdxRPGGame` (implements `ApplicationListener`). UI uses Scene2D (`Stage`, `Skin`, widgets) and 3D models built via `ModelBuilder`.

## Architecture & Data Flow ⚙️
- `DesktopLauncher` starts an LWJGL3 `Lwjgl3Application` configured for this sample game.
- `MechUmbraGdxRPGGame#create()` initializes resources: lights, camera, model batches, models, Scene2D stages and input processing. Render loop lives in `render()` of the same class.
- Global/shared runtime state is exposed as public static fields on `MechUmbraGdxRPGGame` (e.g., `hero`, `enemy`, `timeElapsed`, `counter`, `lastMessage`) that HUD and other screens read directly (see `MainHUDScreen.render`).
- UI patterns: small popup widgets are implemented as `Group` classes with programmatic `Skin` creation (examples: `QuitPopupWidget`, `EnableFightPopupWidget`, `PopupExitScreen`).
- Character model: `Character` is a simple POJO in `core/src/.../rpg/Character.java` with `attack`, `takeDamage`, and `isAlive()`.

## Conventions & Project-specific Patterns ✔️
- Fonts are TTFs located under `assets/fonts/` and created with `FreeTypeFontGenerator` (`MainHUDScreen.createFont` and widget helper methods). Generators are disposed right after font creation — follow this pattern.
- UI skins are created programmatically using a `Pixmap` and `BitmapFont` rather than JSON-based skin files. Copy the pattern in `QuitPopupWidget.createBasicSkin()`.
- 3D models are built inline with `ModelBuilder` / `MeshPartBuilder`. Follow the existing usage for normals/position `Usage.Position | Usage.Normal`.
- Input handling: an inline `InputProcessor` is used inside `MechUmbraGdxRPGGame#create()`. Note: the code uses numeric keycodes (e.g., `152`, `146`) — prefer `Input.Keys.R` / `Input.Keys.F` constants for clarity in changes.
- Resource disposal: dispose `Stage`, `Skin`, `BitmapFont` when a screen/widget is disposed. Examples in `MainHUDScreen.dispose()` and `PopupExitScreen.dispose()`.

## Build / Run / Debug 🧩
- Build everything: `./gradlew build`
- No `application` plugin / Gradle run task is configured; run the game from an IDE by launching `desktop/src/.../DesktopLauncher.java` (IntelliJ/IDEA, Eclipse) with JVM arg on macOS: `-XstartOnFirstThread`.
- To add a quick Gradle run task, add the `application` plugin to `:desktop` and set `mainClass` to `com.berlinbrown.mech.umbra.DesktopLauncher`.
- For native asset loading debug: assets live at repository root `assets/` and are loaded by `Gdx.files.internal()`.

## Typical Small Changes (Examples) ✏️
- Change hero stats: edit initialization in `MechUmbraGdxRPGGame#create()` (lines where `hero.strength`, `hero.healthPoints`, etc. are set).
- Add a screen-based popup: copy `QuitPopupWidget` pattern — create a `Group` or `Screen`, build a basic skin with `Pixmap`, add text/buttons and `ClickListener` handlers.
- Replace numeric keycodes: inside `MechUmbraGdxRPGGame#create()` replace `if (keycode == 152)` with `if (keycode == Input.Keys.R)` and update comments.

## Tests & CI 📋
- There are currently no unit or integration tests in the repo. Prefer small, focused unit tests for non-GDX logic (e.g., `Character.attack/takeDamage`) and use mocking or a headless backend for LibGDX if adding tests for rendering code.

## Pitfalls & Notes ⚠️
- macOS requires `-XstartOnFirstThread` for many LWJGL/LibGDX apps — remember when adding run configurations or CI runners.
- Global mutable `static` state (`hero`, `enemy`, etc.) increases coupling—be careful when extracting or parallelizing systems.
- Assets are referenced by relative paths (e.g., `fonts/Roboto-Regular.ttf`) — ensure `assets/` is on classpath when running outside the IDE.

## Where to look first (jump-to files) 🔎
- Entry & loop: `core/src/.../MechUmbraGdxRPGGame.java` (game loop, model & UI creation)
- UI patterns: `core/src/.../screens/QuitPopupWidget.java`, `EnableFightPopupWidget.java`, `PopupExitScreen.java`, `MainHUDScreen.java`
- Launcher: `desktop/src/.../DesktopLauncher.java`
- Small domain model: `core/src/.../rpg/Character.java`
- Build config: `build.gradle` and `core/build.gradle` (gdxVersion hardcoded to 1.14.0)

---
If you want, I can (1) add a short run task to `desktop/build.gradle` to make `./gradlew :desktop:run` work, (2) replace numeric keycodes with `Input.Keys` constants across the repo, or (3) add a small unit test for `Character.takeDamage`. Which would you like me to do next?