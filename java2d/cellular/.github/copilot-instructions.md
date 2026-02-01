# Copilot / AI agent instructions for this repo

Purpose
- Provide concise, actionable guidance so an AI coding agent can be productive immediately.

Quick start (build & run)
- Build: `mvn -DskipTests package` (produces `target/classes`).
- Run (no assembly): `java -cp target/classes com.berlin.cellular.basic.GraphicsMain` or
  `java -cp target/classes com.berlin.cellular.square.AutomataMain` to start examples.
- Prefer running from an IDE for live debugging (set main class to one above).

Repository structure (essential)
- `src/main/java/com/berlin/cellular/basic` — simple Graphics2D demo.
  - Entry: `GraphicsMain` -> constructs `RenderBasicGraphics`.
- `src/main/java/com/berlin/cellular/square` — squaring automata demo.
  - Entry: `AutomataMain` -> constructs `Cellular`.
- `src/main/java/com/berlin/ai/neural` — small neural demo (`SimpleNeuralNetwork`).

Architecture & important patterns
- UI thread rules: all Swing UI updates use `EventQueue.invokeLater` or `SwingUtilities.invokeLater`.
  - Keep UI updates on the EDT. See `RenderBasicGraphics.invokeLater()` and `Cellular.invokeLater()`.
- Timing & scheduling:
  - `RenderBasicGraphics.BasicGraphicsCanvas` uses a `ScheduledExecutorService` (preferred).
  - `Cellular.JavaCellularAutomataSquareCanvas` uses `java.util.Timer` with a `TimerTask`.
  - When changing timing, follow the repo pattern: schedule background updates, then call `repaint()` on EDT.
- Double-buffering: both canvases use off-screen images and override `update(Graphics)` to paint into an image, then flip.
  - Follow this pattern when adding new rendering code to avoid flicker.
- Cellular rules & data flow:
  - `RenderBasicGraphics` keeps `boolean[][] cells` and computes `nextCells`, then swaps arrays.
  - Neighbor counting in `RenderBasicGraphics.countNeighbors` wraps edges (toroidal grid).
  - `Cellular.SquaringAutomata` exposes `CellGridSystem` with `createGridSystem()` and `nextGenerationRules()` — use these methods for inspecting or extending rules.

Conventions used in code
- Main classes end with `Main` and call an `invokeLater()` or `invoke` style method to start UI.
- Rendering code lives inside inner `JPanel` subclasses named `*Canvas` and implements `paint`/`update` double-buffering.
- Use of small utility classes: inner `Cell` / `CellEntity` objects represent grid cells.

Editing and debugging tips
- To iterate quickly: `mvn -DskipTests package` then run the appropriate `main` class with `java -cp target/classes <mainClass>`.
- For breakpoints: run from an IDE and set breakpoints inside `paint(...)`, `updateCellularAutomata()`, or `nextGenerationRules()`.
- Logging: classes use `System.out.println` for simple tracing; prefer using that for quick prints.

What to watch for when changing code
- Thread-safety: ensure long-running computation is off the EDT and only lightweight UI updates occur on the EDT.
- Rendering performance: any per-frame allocation in `paint()` will be visible; prefer preallocated off-screen buffers.
- Timing consistency: prefer `ScheduledExecutorService` over `Timer` for predictable scheduling.

Files to reference for examples
- Basic rendering: `src/main/java/com/berlin/cellular/basic/RenderBasicGraphics.java`
- Basic entry: `src/main/java/com/berlin/cellular/basic/GraphicsMain.java`
- Squaring automata: `src/main/java/com/berlin/cellular/square/Cellular.java`
- Neural demo: `src/main/java/com/berlin/ai/neural/SimpleNeuralNetwork.java`

If anything is unclear
- Ask for the specific goal (bugfix, feature, performance) and which sample (basic vs square) to target, and provide a minimal reproduction (steps, expected vs actual).

End of file.
