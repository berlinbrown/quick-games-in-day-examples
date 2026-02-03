# quick-games-in-day-examples

Quick Games in a Day You Can Build - Java, OpenGL, Unity


## Libgdx Game Demo

## Mech Umbra — Mini RPG (mechumbra-samples) 🔧

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



## Cellular Game Examples

### Basic Cellular Automata Again

This repository contains small Java 2D demos that demonstrate cellular automata, simple rendering
patterns with Java2D/Swing, and a tiny neural demo for reference.

Demos
- Basic graphics & Game of Life (`com.berlin.cellular.basic`)
	- Entry point: `GraphicsMain` -> constructs `RenderBasicGraphics`.
	- Implements Conway's Game of Life on a toroidal grid (`boolean[][] cells`).
	- Rendering uses double-buffering (off-screen Image) by overriding `update(Graphics)` and
		painting to an off-screen `Graphics2D` before flipping to screen.
	- Scheduling: background updates are scheduled with a `ScheduledExecutorService` and UI
		repaints are invoked on the EDT using `EventQueue.invokeLater`.
	- Interactive elements:
		- Player: a movable dark-gray box controlled with arrow keys (`playerX`, `playerY`).
		- Observer: a one-cell dark-blue entity that bounces horizontally across the grid and
			collects "food" from active Game-of-Life cells. Each collection adds `0.5` food and
			removes (deactivates) the cell.

- Squaring automata (`com.berlin.cellular.square`)
	- Entry point: `AutomataMain` -> constructs `Cellular`.
	- Implements a Wolfram-style squaring rule with `SquaringAutomata.CellGridSystem`.
	- Uses `java.util.Timer` and `TimerTask` for periodic repainting and also uses
		double-buffering for smooth rendering.

- Neural toy (`com.berlin.ai.neural`)
	- `SimpleNeuralNetwork` is a minimal single-neuron example with a `main()` for quick runs.

Build & Run
- Build with Maven (skip tests for faster cycles):

```bash
mvn -DskipTests package
```

- Run the basic graphics demo:

```bash
java -cp target/classes com.berlin.cellular.basic.GraphicsMain
```

- Run the squaring automata demo:

```bash
java -cp target/classes com.berlin.cellular.square.AutomataMain
```

Developer notes / key patterns
- Keep long-running or periodic logic off the EDT; only call `repaint()` on the EDT.
- Prefer `ScheduledExecutorService` over `Timer` where consistent timing and cancellation
	semantics are needed (see `RenderBasicGraphics.BasicGraphicsCanvas`).
- Avoid per-frame heap allocations in `paint()`; re-use off-screen buffers and precomputed shapes.
- Grid coordinate mapping: pixel X/Y for a cell = `gridMargin + col*cellSize`, `gridMargin + row*cellSize`.

Common edit targets
- `RenderBasicGraphics.java` — modify rendering, game rules, or add actors (player/observer).
- `Cellular.java` — modify squaring rules and `SquaringAutomata.CellGridSystem`.
- `SimpleNeuralNetwork.java` — small runnable example if you want a quick Java run.

If anything is unclear or you want a different level of detail (examples, diagrams, CI steps),
tell me which section to expand and I will update this README accordingly.

