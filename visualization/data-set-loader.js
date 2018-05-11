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
			.nodeLabel('user')
			.nodeAutoColorBy('user')
			.graphData({ nodes, links });

		function jsongraph(object, parentKey) {
			var id = keys[parentKey];
			// if (typeof id === 'undefined') {
				// only create new nodes when there's a new parentKey
				var pid = newkey++;
				keys[parentKey] = pid;
				id = keys[parentKey];
				var node = { id: id, user: parentKey };
				nodes.push(node);
			// }
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

    return [loadJSONSchema];
}
