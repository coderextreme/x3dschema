var fs = require('fs');
var jjv = require('jjv');

var X3dJsonSchema = {
        "$schema": "https://json-schema.org/draft/2020-12/schema",
        "title": "JSON Schema X3D V3.3",
        "description": "Development JSON Schema for X3D V3.3 ",
        "type": "object",
        "properties": {
		"@geoSystem": {
		    "description": "Attempts to validate all possible combinations",
		    "oneOf": [
			{
			    "type": "array",
			    "minItems": 1,
			    "maxItems": 3,
			    "items": [
				{
				    "type": "string",
				    "enum": [
					"GD",
					"GDC"
				    ],
				    "default": "GD"
				},
				{
				    "type": "string",
				    "enum": [
					"AM",
					"AN",
					"BN",
					"BR",
					"CC",
					"CD",
					"EA",
					"EB",
					"EC",
					"ED",
					"EE",
					"EF",
					"FA",
					"HE",
					"HO",
					"ID",
					"IN",
					"KA",
					"RF",
					"SA",
					"WD",
					"WE"
				    ],
				    "default": "WE"
				}
			    ],
			    "additionalItems": {
				"type": "string",
				"enum": [
				    "WGS84"
				]
			    }
			},
			{
			    "type": "array",
			    "minItems": 1,
			    "maxItems": 5,
			    "items": [
				{
				    "type": "string",
				    "enum": [
					"UTM"
				    ],
				    "default": "GD"
				},
				{
				    "type": "string",
				    "enum": [
					"Z01",
					"Z1",
					"Z02",
					"Z2",
					"Z03",
					"Z3",
					"Z04",
					"Z4",
					"Z05",
					"Z5",
					"Z06",
					"Z6",
					"Z07",
					"Z7",
					"Z08",
					"Z8",
					"Z09",
					"Z9",
					"Z10",
					"Z11",
					"Z12",
					"Z13",
					"Z14",
					"Z15",
					"Z16",
					"Z17",
					"Z18",
					"Z19",
					"Z20",
					"Z21",
					"Z22",
					"Z23",
					"Z24",
					"Z25",
					"Z26",
					"Z27",
					"Z28",
					"Z29",
					"Z30",
					"Z31",
					"Z32",
					"Z33",
					"Z34",
					"Z35",
					"Z36",
					"Z37",
					"Z38",
					"Z39",
					"Z40",
					"Z41",
					"Z42",
					"Z43",
					"Z44",
					"Z45",
					"Z46",
					"Z47",
					"Z48",
					"Z49",
					"Z50",
					"Z51",
					"Z52",
					"Z53",
					"Z54",
					"Z55",
					"Z56",
					"Z57",
					"Z58",
					"Z59",
					"Z60"
				    ],
				    "default": "WE"
				},
				{
				    "type": "string",
				    "enum": [
					"N",
					"S"
				    ]
				},
				{
				    "type": "string",
				    "enum": [
					"AM",
					"AN",
					"BN",
					"BR",
					"CC",
					"CD",
					"EA",
					"EB",
					"EC",
					"ED",
					"EE",
					"EF",
					"FA",
					"HE",
					"HO",
					"ID",
					"IN",
					"KA",
					"RF",
					"SA",
					"WD",
					"WE"
				    ]
				}
			    ],
			    "additionalItems": {
				"type": "string",
				"enum": [
				    "WGS84"
				]
			    }
			},
			{
			    "type": "array",
			    "minItems": 1,
			    "maxItems": 1,
			    "items": [
				{
				    "type": "string",
				    "enum": [
					"GC",
					"GCC"
				    ]
				}
			    ]
			}
		    ]
		}
	}
};

var env = jjv();
env.addSchema('x3djson', X3dJsonSchema);

function validateJSON() {
		var hello = {
			"@geoSystem":["UTM","Z10","N"]
		};

		var errors = env.validate('x3djson', hello);

		if (!errors) {
			console.log('jjv Valid');
		} else {
			console.log('jjv Validation failed.');
			console.log('jjv Validation errors:');
			console.log(JSON.stringify(errors));
		}
}

validateJSON();
