# Basic Cellular Automata Again

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

