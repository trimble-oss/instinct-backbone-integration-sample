# Data Sources

The keys and types of data stored in Backbone.

All data is stored as [BackboneData](BackboneResult.md) containing:
* The time the data was sent from its source
* The time the data was received by the Backbone
* The actual value

## Backbone Keys
Different data sources provide different value types and have a unique ID.
The Backbone Keys are used to identify data sources in the Backbone.

|Key Name                  |Unique ID String                  |Value Type         |
|:-------------------------|:---------------------------------|:------------------|
|ECM_CONNECTION_KEY        |vehicle/ecm/connection            |Boolean            |
|ENGINE_HOURS_KEY          |vehicle/ecm/engine/hours          |Double             |
|ENGINE_ODOMETER_KM_KEY    |vehicle/ecm/odometer_km           |Double             |
|ENGINE_ON_KEY             |vehicle/ecm/engine/on             |Boolean            |
|ENGINE_SPEED_KMH_KEY      |vehicle/ecm/speed_kmh             |Double             |
|GPS_DEGREES_KEY           |vehicle/gps_degrees               |GpsData            |
|IGNITION_KEY              |vehicle/ecm/ignition              |Boolean            |
|OBC_ID_KEY                |vehicle/obc/id                    |ObcID              |
|TIME_ENGINE_ON_SECONDS_KEY|vehicle/ecm/engine/time_on_seconds|Int                |
|VEHICLE_ID_KEY            |vehicle/id                        |String             |
|VIN_KEY                   |vehicle/ecm/vin                   |String             |

## GpsData
GpsData contains:

* Latitude in decimal degrees
* Longitude in decimal degrees
* Accuracy of the GPS fix in meters
* The time the GPS fix was acquired

## ObcID
ObcID contains:

* An identifier of the type of OBC
* The unique serial number of the connected OBC 