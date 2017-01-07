CREATE DATABASE openflights;

CREATE USER openflights@localhost;
GRANT ALL PRIVILEGES ON openflights.* TO openflights@localhost;

CONNECT openflights;

DROP TABLE IF EXISTS `airlines`;
CREATE TABLE `airlines` (
  `alid` int(11) NOT NULL auto_increment,
  `name` text,
  `alias` text,
  `iata` varchar(2) default NULL,
  `icao` varchar(3) default NULL,
  `callsign` text,
  `country` text,
  `active` varchar(1) default 'N',
  PRIMARY KEY  (`alid`),
  KEY `iata` (`iata`),
  KEY `icao` (`icao`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `airports`;
CREATE TABLE `airports` (
  `apid` int(11) NOT NULL auto_increment,
  `name` text NOT NULL,
  `city` text,
  `country` text,
  `iata` varchar(3) default NULL,
  `icao` varchar(4) default NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  `elevation` int(11) default NULL,
  `timezone` float default NULL,
  `dst` char(1) default NULL,
  `tz_id` text,
  PRIMARY KEY  (`apid`),
  KEY `x` (`x`),
  KEY `y` (`y`),
  KEY `iata` (`iata`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX `iata_idx` ON airports(iata);

DROP TABLE IF EXISTS `routes`;
CREATE TABLE `routes` (
  `airline` varchar(3) default NULL,
  `alid` int(11) default NULL,
  `src_ap` varchar(4) default NULL,
  `src_apid` int(11) default NULL,
  `dst_ap` varchar(4) default NULL,
  `dst_apid` int(11) default NULL,
  `codeshare` text,
  `stops` text,
  `equipment` text,
  `rid` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`rid`),
  UNIQUE KEY `alid` (`alid`,`src_apid`,`dst_apid`),
  KEY `src_apid` (`src_apid`),
  KEY `dst_apid` (`dst_apid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;