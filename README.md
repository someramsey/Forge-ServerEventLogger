# Server Event Logger (Forge)
#### A server sided minecraft forge mod to record common player behaviour and events for moderation.

#### Recorded Data
- Player Join/Leave
- Player Death/Spawn
- Block Break/Place
- Chat Messages
- Dimension Change
- Container interactions (Open, Close, Slot Change)

### Usage
The mod will log all events to a local file. The log file is encoded in binary format, the jar file provides useful functions for reading and decoding the log file. Download the jar file, create a new java project and add the file as a dependency. Usee the `com.ramsey.Deserializer` class to access provided functions.

### Download and Installation
1. Download the latest release from the [releases page](https://github.com/someramsey/Forge-ServerEventLogger/releases/)
2. Place the downloaded jar file in the `mods` folder of your forge server.
3. Start the server and the mod will generate a config file in the `config` folder.
4. Edit the config file if you want to change the log file location.

![image](https://github.com/user-attachments/assets/5d6a9fce-463c-45aa-ad98-3d25b6ee7cdf)
