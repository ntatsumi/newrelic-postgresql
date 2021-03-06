# New Relic Platform PostgreSQL Plugin (Prototype)

## Requirements

- A New Relic account
- Java Runtime (JRE) environment Version 1.6 or later
- [monitor-bridge](https://github.com/blackboard/monitor-bridge)
- PostgreSQL instance with pg_stat_statement

----

## Installation

#### Step 0 - Building the Plugin
```
        ant dist
```

#### Step 1 - Extracting the Plugin
```
	tar -xvzf newrelic_postgresql_plugin-X.Y.Z.tar.gz
```

#### Step 2 - Configuring the Plugin

Check out the [configuration information](#configuration-information) section for details on configuring your plugin. 

#### Step 3 - Running the Plugin

Can run as a normal process or installed as a daemon service.

##### Windows
To run the plugin, execute the following command from a command window:

```
	wrapper\plugin.bat
```

or 

```
	java -Xmx128m -jar plugin.jar
```

**Note:** Though it is not necessary, the '-Xmx128m' flag is highly recommended due to the fact that when running the plugin on a server class machine, the `java` command will start a JVM that may reserve up to one quarter (25%) of available memory, but the '-Xmx128m' flag will limit heap allocation to a more reasonable 128MBs.  

To install the plugin as a service, execute the following command from a command window

```
  wrapper\InstallPlugin.bat
```

To run the installed service, execute the following command from a command window

```
  wrapper\StartPlugin.bat
```

To stop the installed service, execute the following command from a command window

```
  wrapper\StopPlugin.bat
```

To uninstall plugin, execute the following command from a command window

```
  wrapper\UninstallPlugin.bat
```
##### Linux/Mac OS X
 
To run the plugin, execute the following command from a terminal or command window (assuming Java is installed and on your path):

```
	./wrapper/plugin.sh console
```

or directly 

```
	java -Xmx128m -jar plugin.jar
```

**Note:** Though it is not necessary, the '-Xmx128m' flag is highly recommended due to the fact that when running the plugin on a server class machine, the `java` command will start a JVM that may reserve up to one quarter (25%) of available memory, but the '-Xmx128m' flag will limit heap allocation to a more reasonable 128MBs.  

To install the plugin as a daemon, execute the following command from a terminal window

```
  ./wrapper/plugin.sh install
```

To run the installed daemon, execute the following command from a terminal window

```
  ./wrapper/plugin.sh start
```

To stop the installed service, execute the following command from a terminal window

```
  ./wrapper/plugin.sh stop
```

To uninstall plugin, execute the following command from a terminal window

```
  ./wrapper/plugin.sh remove
```

**Note:** You will need to make the script executable, use chmod +x ./wrapper/plugin.sh 

----

## Configuration Information

### Configuration Files

You will need to modify two configuration files in order to set this plugin up to run.  The first (`newrelic.json`) contains configurations used by all Platform plugins (e.g. license key, logging information, proxy settings) and can be shared across your plugins.  The second (`plugin.json`) contains data specific to each plugin such as a list of hosts and port combination for what you are monitoring.  Templates for both of these files should be located in the '`config`' directory in your extracted plugin folder. 

#### Configuring the `plugin.json` file: 

The `plugin.json` file has a provided template in the `config` directory named `plugin.template.json`.  Make a copy of this template file and rename it to `plugin.json` (the New Relic Platform Installer will automatically handle creation of configuration files for you).  

Below is an example of the `plugin.json` file's contents, you can add multiple objects to the "agents" array to query PostgreSQL information from [monitor-bridge](https://github.com/blackboard/monitor-bridge) cluster:

```
{
  "agents": [
    {
      "name" : "postgres01",
      "monitor-bridge" : "monitor-bridge01.pd.local",
      "postgresql" : "postgres01.pd.local"
    },
    {
      "name" : "postgres02",
      "monitor-bridge" : "monitor-bridge01.pd.local",
      "postgresql" : "postgres02.pd.local"
    }
  ]
}
```

**note** - The "name" attribute is used to identify specific instances in the New Relic UI. 

#### Configuring the `newrelic.json` file: 

The `newrelic.json` file also has a provided template in the `config` directory named `newrelic.template.json`.  If you are installing manually, make a copy of this template file and rename it to `newrelic.json` (again, the New Relic Platform Installer will automatically handle this for you).  

The `newrelic.json` is a standardized file containing configuration information that applies to any plugin (e.g. license key, logging, proxy settings), so going forward you will be able to copy a single `newrelic.json` file from one plugin to another.  Below is a list of the configuration fields that can be managed through this file:

##### Configuring your New Relic License Key

Your New Relic license key is the only required field in the `newrelic.json` file as it is used to determine what account you are reporting to.  If you do not know what your license key is, you can learn about it [here](https://newrelic.com/docs/subscriptions/license-key).

Example: 

```
{
  "license_key": "YOUR_LICENSE_KEY_HERE"
}
```

##### Logging configuration

By default Platform plugins will have their logging turned on; however, you can manage these settings with the following configurations:

`log_level` - The log level. Valid values: [`debug`, `info`, `warn`, `error`, `fatal`]. Defaults to `info`.

`log_file_name` - The log file name. Defaults to `newrelic_plugin.log`.

`log_file_path` - The log file path. Defaults to `logs`.

`log_limit_in_kbytes` - The log file limit in kilobytes. Defaults to `25600` (25 MB). If limit is set to `0`, the log file size would not be limited.

Example:

```
{
  "license_key": "YOUR_LICENSE_KEY_HERE"
  "log_level": "debug",
  "log_file_path": "/var/logs/newrelic"
}
```

##### Proxy configuration

If you are running your plugin from a machine that runs outbound traffic through a proxy, you can use the following optional configurations in your `newrelic.json` file:

`proxy_host` - The proxy host (e.g. `proxy.pd.local`)

`proxy_port` - The proxy port (e.g. `8083`).  Defaults to `80` if a `proxy_host` is set

`proxy_username` - The proxy username

`proxy_password` - The proxy password

Example:

```
{
  "license_key": "YOUR_LICENSE_KEY_HERE",
  "proxy_host": "proxy.pd.local",
  "proxy_port": 8083
}
```

