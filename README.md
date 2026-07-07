# Geasd

Harvest **wheat seeds** from vanilla **grass** and **ferns** in Minecraft **26.1.2**.

Geasd adds an early-game way to gather wheat seeds without waiting for village loot or dungeon chests. Right-click plants with an empty hand or shears, roll for seed drops, and optionally leave behind tiny **stubble** that regrows over time.

![Geasd Features](https://github.com/quackawop-rgb/Geasd/raw/main/docs/geasd-features-infographic.png)

---

## Table of Contents

- [Features](#features)
- [How It Works](#how-it-works)
- [Supported Plants](#supported-plants)
- [Requirements](#requirements)
- [Installation](#installation)
- [Multiplayer](#multiplayer)
- [Configuration](#configuration)
- [Building from Source](#building-from-source)
- [Bug Reports](#bug-reports)
- [License](#license)
- [Disclaimer](#disclaimer)

---

## Features

- Harvest **Wheat Seeds** from four vanilla plant types
- **Empty-hand harvesting** for quick, one-time breaks
- **Shears harvesting** for better seed yields and sustainable regrowth
- **Plant Stubble** — tiny biome-tinted remnants that regrow into the original plant
- Seeds drop on the **ground** as items (not auto-added to inventory)
- Shears play the **snip** sound and **arm swing** animation when harvesting
- Works in **singleplayer** and **multiplayer** (client + server)

---

## How It Works

### Empty Hand — Right-Click

| | |
|---|---|
| **Action** | Right-click a supported plant with an empty hand |
| **Result** | The plant is fully removed (no regrowth) |
| **Seed drop** | **85%** → 1 seed · **15%** → 2 seeds |
| **Durability** | Nothing consumed |

Best for quick one-off seed gathering when you don't need the plant to come back.

---

### Shears — Right-Click (Sustainable Harvest)

| | |
|---|---|
| **Action** | Right-click a supported plant with shears |
| **Result** | Plant becomes tiny **Plant Stubble** that regrows later |
| **Seed drop** | **10%** → 1 seed · **90%** → 2 seeds |
| **Durability** | 1 shears durability per harvest |
| **Regrowth** | Stubble restores the original plant after **6,000 ticks** (~**5 minutes**) |

Best for farming seeds repeatedly from the same spot without replanting.

When stubble regrows, it restores the correct plant type — short grass, tall grass, fern, or tall fern — including both halves for tall plants.

---

### Shears — Left-Click (Permanent Break)

| | |
|---|---|
| **Action** | Break the plant normally while holding shears |
| **Result** | Plant is permanently removed (no stubble, no regrowth) |
| **Seed drop** | **10%** → 1 seed · **90%** → 2 seeds |

Same seed odds as shears right-click, but the plant is gone for good.

---

### Breaking Stubble

If stubble already exists from a previous shears harvest:

- **Right-click** (empty hand or shears) removes the stubble permanently
- **No seeds** are dropped from breaking stubble alone
- Stubble will not regrow once broken

---

## Supported Plants

Geasd only affects these vanilla blocks:

| Plant | Block |
|-------|-------|
| Short Grass | `minecraft:short_grass` |
| Tall Grass | `minecraft:tall_grass` |
| Fern | `minecraft:fern` |
| Tall Fern | `minecraft:large_fern` |

Tall plants (Tall Grass, Tall Fern) work from either the top or bottom half — the whole plant is handled together.

No other blocks are affected.

---

## Requirements

| Component | Version |
|-----------|---------|
| Minecraft | **26.1.2** |
| Fabric Loader | **0.19.2+** |
| Fabric API | Required |
| Java (build from source only) | **25+** |

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft **26.1.2**
2. Download **Fabric API** for 26.1.2 from [Modrinth](https://modrinth.com/mod/fabric-api) or [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Download the latest **Geasd** JAR from [Releases](https://github.com/quackawop-rgb/Geasd/releases)
4. Place `geasd-1.0.0.jar` in your `.minecraft/mods` folder
5. Launch the game

---

## Multiplayer

Geasd is a **client + server** mod.

| Side | Required? | Why |
|------|-----------|-----|
| **Server** | Yes | Runs harvest logic, seed drops, stubble blocks, and regrowth |
| **Client** | Yes | Renders stubble models and biome tinting |

- **Singleplayer:** One install is enough — both sides load automatically
- **Multiplayer:** Every player **and** the server must have Geasd installed

---

## Configuration

Default values are defined in code (`Config.java`) and are not yet exposed as an in-game config file:

| Setting | Default | Description |
|---------|---------|-------------|
| Hand one-seed chance | `0.85` (85%) | Chance of 1 seed when hand-harvesting |
| Shears one-seed chance | `0.10` (10%) | Chance of 1 seed when shears-harvesting |
| Regrowth ticks | `6000` | Ticks until stubble regrows (~5 minutes) |

The complementary chance always applies for 2 seeds (15% hand, 90% shears).

---

## Building from Source

```powershell
git clone https://github.com/quackawop-rgb/Geasd.git
.\gradlew.bat build
```
Output JAR: build/libs/geasd-1.0.0.jar
Run a development client:
```powershell
.\gradlew.bat runClient
```

## Bug Reports

Found a bug or have a feature idea? **[Open an issue](https://github.com/quackawop-rgb/Geasd/issues)**.

Please include:

- Minecraft version
- Fabric Loader and Fabric API versions
- Geasd version
- Singleplayer or multiplayer
- Steps to reproduce
- Logs or crash reports if applicable

## License

**All Rights Reserved.**

The name **Geasd**, mod ID **`geasd`**, source code, assets, and compiled JAR may not be copied, reuploaded, or rebranded without permission.

You may:

- Install and play the mod personally
- Credit Geasd in videos, streams, and private modpacks

You may not:

- Reupload the mod or claim it as your own
- Use the **Geasd** name for unrelated projects or rebranded forks
- Distribute modified versions without permission
