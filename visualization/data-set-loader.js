function getGraphDataSets() {

    const loadJSONSchema = function(Graph) {
        $.getJSON('x3d-4.0-JSONSchema.json', function(schema) {
		const keys = {};
		let newkey = 0;
		const nodes = [];
		const links = [];

		jsongraph(schema, "X3D JSON Schema");

		Graph
			.nodeId('id')
			.nodeLabel('element')
			.nodeAutoColorBy('element')
			.graphData({ nodes, links });

		function jsongraph(object, parentKey) {
			var pid = newkey++;
			keys[parentKey] = pid;
			var id = keys[parentKey];
			var node = { id: id, element: parentKey };
			nodes.push(node);
			for (var key in object) {
				if (typeof object[key] === 'object') {
					var childid = jsongraph(object[key], key);
					var link = { source: id , target: childid};
					links.push(link);
				}
			}
			return id;
		}
	 });
    };
    loadJSONSchema.description = "<em>X3D V4.0 JSON Schema</em> data";

    const loadJSONSchema2 = function(Graph) {
        $.getJSON('x3d-4.0-JSONSchema.json', function(schema) {
		const keys = {};
		let newkey = 0;
		const nodes = [];
		const links = [];

		jsongraph(schema, "X3D JSON Schema");

		Graph
			.nodeId('id')
			.nodeLabel('element')
			.nodeAutoColorBy('element')
			.graphData({ nodes, links });

		function jsongraph(object, parentKey) {
			var id = keys[parentKey];
			if (typeof id === 'undefined') {
				// only create new nodes when there's a new parentKey
				var pid = newkey++;
				keys[parentKey] = pid;
				id = keys[parentKey];
				var node = { id: id, element: parentKey };
				nodes.push(node);
			}
			for (var key in object) {
				if (typeof object[key] === 'object') {
					var childid = jsongraph(object[key], key);
					var link = { source: id , target: childid};
					links.push(link);
				}
			}
			return id;
		}
	 });
    };
    loadJSONSchema2.description = "<em>X3D V4.0 JSON Schema</em> data, no duplicates";

    const loadJSONSchema3 = function(Graph) {
        $.getJSON('x3d-3.3-JSONSchema.json.backup', function(schema) {
		const keys = {};
		let newkey = 0;
		const nodes = [];
		const links = [];

		jsongraph(schema, "X3D JSON Schema");

		Graph
			.nodeId('id')
			.nodeLabel('element')
			.nodeAutoColorBy('element')
			.graphData({ nodes, links });

		function jsongraph(object, parentKey) {
			var id = keys[parentKey];
			if (typeof id === 'undefined') {
				// only create new nodes when there's a new parentKey
				var pid = newkey++;
				keys[parentKey] = pid;
				id = keys[parentKey];
				var node = { id: id, element: parentKey };
				nodes.push(node);
			}
			for (var key in object) {
				if (typeof object[key] === 'object') {
					var childid = jsongraph(object[key], key);
					var link = { source: id , target: childid};
					links.push(link);
				}
			}
			return id;
		}
	 });
    };
    loadJSONSchema3.description = "<em>X3D V3.3 JSON Schema</em> data, no duplicates";

    return [loadJSONSchema, loadJSONSchema2, loadJSONSchema3];
}
