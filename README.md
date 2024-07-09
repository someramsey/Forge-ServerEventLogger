# ServerEventLogger (Forge)
#### A server sided minecraft forge mod to record common player behaviour and events for moderation.

#### Recorded Data
- Player Join/Leave
- Player Death/Spawn
- Block Break/Place
- Chat
- Dimension Change

The logged events are consequently written to a local file in JSON format. (The file will not be a valid JSON, the events are individually written to the file, not as an array).

### Config
- `eventClumpSize` - Determines how many events are in a single batch. Default: 10
- `eventStreamOutputPath` - The local path to the file where the events will be written. Default: "./events.json"
- `streamTunnelListenPort` - The port the stream tunnel server will listen on. Default: 25565