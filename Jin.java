import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Document;
import org.web3d.x3d.jsail.X3DLoaderDOM;
import org.web3d.x3d.jsail.Core.X3D;
import org.web3d.x3d.sai.Core.X3DNode;
import org.web3d.x3d.sai.Shape.X3DAppearanceNode;
import org.web3d.x3d.sai.Rendering.X3DGeometryNode;
import org.web3d.x3d.sai.Rendering.X3DCoordinateNode;
import org.web3d.x3d.jsail.HAnim.HAnimHumanoid;
import org.web3d.x3d.jsail.HAnim.HAnimJoint;
import org.web3d.x3d.jsail.HAnim.HAnimSite;
import org.web3d.x3d.jsail.HAnim.HAnimSegment;
import org.web3d.x3d.jsail.Grouping.Transform;
import org.web3d.x3d.jsail.Shape.Shape;
import org.web3d.x3d.jsail.Geometry3D.IndexedFaceSet;
import org.web3d.x3d.jsail.Shape.Appearance;
import org.web3d.x3d.jsail.Rendering.Coordinate;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.List;
import java.util.Iterator;
import java.lang.reflect.Method;

class Jin {
	static int HUMANCHILD = 3;
	static float height = 1.87f;
	static float scaledHeight = 0;
	static float x = 0;
	static float y = 0;
	static float l_x = 0;
	static float l_z = 0;
	static float r_x = 0;
	static float r_z = 0;
	static float maxy = 0;
	static float z = 0;
	static float[] scale = new float[] { 1, 1, 1 };
	static float yscale = 1;
	public static void main(String [] args) throws Exception {
		for (int a = 0; a < args.length; a++) {
			if (args[a].startsWith("-X")) {
				continue;
			}
			/*
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "Script");

			File f = new File("examples/JinLOA4.x3d");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(f);

						File xmlf = new File("examples/JinLOA4xml.x3d");
						FileWriter fw = new FileWriter(xmlf);
						DOMSource source = new DOMSource(document);
						StreamResult streamResult = new StreamResult(fw);
						transformer.transform(source, streamResult);
						fw.close();

			X3DLoaderDOM xmlLoader = new X3DLoaderDOM();
			X3D X3D0 = (X3D)xmlLoader.toX3dModelInstance(document);
			*/
			Class  clz = Class.forName(args[a]);
			Object inst = clz.newInstance();
			Method method = clz.getDeclaredMethod("getRootNodeList");
			List<X3D> rots = (List<X3D>)method.invoke(inst);

			Iterator<X3D> i = rots.iterator();
			int count = 0;
			while (i.hasNext()) {
				count++;
				X3D X3D0 = i.next();
				HAnimHumanoid human = (HAnimHumanoid)X3D0.getScene().getChildren().get(HUMANCHILD);
				scale = human.getScale();
				human.setScale(new float[] { 1, 1, 1 });
				System.err.println(human.getName());
				System.err.println(scale[0]+" "+scale[1]+" "+scale[2]+" ");
				HAnimJoint root = (HAnimJoint)human.getSkeleton()[0];
				System.err.println(root.getName());
				float[] center = root.getCenter();
				System.err.println(center[0]+" "+center[1]+" "+center[2]+" ");
				x = center[0];
				y = center[1];
				maxy = center[1];
				z = center[2];
				float [] translation = new float[] {0, 0, 0};
				try {
					centering(root);
					x = (l_x + r_x) / 2;
					z = (l_z + r_z) / 2;
					yscale = maxy - y;
					scaledHeight = yscale * scale[1];
					System.err.println("max y "+maxy+" y "+y+" yscale "+yscale+" height "+height);
					transform(root, translation);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
				X3D0.toFileX3D("examples/"+args[a]+"scaled"+count+".x3d");

				HUMANCHILD = 3;
				height = 1.87f;
				scaledHeight = 0;
				x = 0;
				y = 0;
				l_x = 0;
				l_z = 0;
				r_x = 0;
				r_z = 0;
				maxy = 0;
				z = 0;
				scale = new float[] { 1, 1, 1 };
				yscale = 1;
			}
		}
	}
	static void centering(HAnimJoint joint) {
		float [] center = joint.getCenter();
		String name = joint.getName();
		// System.err.println(name);
		// System.err.println(center[0]+" "+center[1]+" "+center[2]+" ");
		switch (name) {
			case HAnimJoint.NAME_L_TARSAL_DISTAL_INTERPHALANGEAL_5:
				l_x = center[0];
				l_z = center[2];
				break;
			case HAnimJoint.NAME_R_TARSAL_DISTAL_INTERPHALANGEAL_5:
				r_x = center[0];
				r_z = center[2];
				break;
		}
		if (center[1] >= maxy) {
			maxy = center[1];
		}
		if (center[1] <= y) {
			y = center[1];
		}
		ArrayList<X3DNode> children = joint.getChildrenList();
		for (int ch = 0; ch < children.size(); ch++) {
			X3DNode child = children.get(ch);
			if (child instanceof HAnimJoint) {
				centering((HAnimJoint)child);
			}
		}
	}
	static void transform(float[] point, int point_offset, float [] translation) {
		/// System.err.println("OLD "+point[point_offset+0]+" "+point[point_offset+1]+" "+point[point_offset+2]+" ");
		point[point_offset+0] -= x;
		point[point_offset+1] -= y;
		point[point_offset+2] -= z;

		point[point_offset+0] *= scale[0] * height / scaledHeight;
		point[point_offset+1] *= scale[1] * height / scaledHeight;
		point[point_offset+2] *= scale[2] * height / scaledHeight;

		point[point_offset+0] += translation[0];
		point[point_offset+1] += translation[1];
		point[point_offset+2] += translation[2];
		// System.err.println("NEW "+point[point_offset+0]+" "+point[point_offset+1]+" "+point[point_offset+2]+" ");
	}
	static boolean transform(X3DNode node, float [] parentTranslation) {
		float [] translation = new float[] {parentTranslation[0], parentTranslation[1], parentTranslation[2]};
		ArrayList<X3DNode> children = null;
		if (node instanceof HAnimJoint) {
			HAnimJoint joint = (HAnimJoint)node;

			float [] field = joint.getTranslation();
			transform(field, 0, new float[] { 0, 0, 0});
			transform(translation, 0, field);
			joint.setTranslation(new float[] {0, 0, 0});

			float [] center = joint.getCenter();
			transform(center, 0, translation);
			joint.setCenter(center);
			children = joint.getChildrenList();
		} else if (node instanceof HAnimSite) {
			HAnimSite site = (HAnimSite)node;

			float [] field = site.getTranslation();
			transform(field, 0, new float[] { 0, 0, 0});
			transform(translation, 0, field);
			site.setTranslation(new float[] {0, 0, 0});

			float [] center = site.getCenter();
			transform(center, 0, translation);
			site.setCenter(center);

			children = site.getChildrenList();
		} else if (node instanceof HAnimSegment) {
			HAnimSegment segment = (HAnimSegment)node;
			X3DNode coord = segment.getCoord();
			if (coord != null) {
				if (!transform(coord, translation)) {
					System.err.println("Unpacking coord in HAnimSegment failed");
				}
			}
			ArrayList<X3DNode> displacers = segment.getDisplacersList();
			if (displacers != null) {
				for (int di = 0; di < displacers.size(); di++) {
					X3DNode displacer = displacers.get(di);
					if (!transform(displacer, translation)) {
						System.err.println("Unpacking displacer in HAnimSegment failed");
					}
				}
			}
			children = segment.getChildrenList();
		} else if (node instanceof Transform) {
			Transform trans = (Transform)node;

			float [] field = trans.getTranslation();
			transform(field, 0, new float[] { 0, 0, 0});
			transform(translation, 0, field);
			trans.setTranslation(new float[] {0, 0, 0});

			children = trans.getChildrenList();
		} else if (node instanceof Shape) {
			Shape shape = (Shape)node;
			X3DAppearanceNode appearance = shape.getAppearance();
			if (!transform(appearance, translation)) {
				System.err.println("Unpacking appearance in Shape failed");
			}
			X3DGeometryNode geometry = shape.getGeometry();
			if (!transform(geometry, translation)) {
				System.err.println("Unpacking geometry in Shape failed");
			}
		} else if (node instanceof IndexedFaceSet) {
			IndexedFaceSet ifs = (IndexedFaceSet)node;
			X3DCoordinateNode coord = ifs.getCoord();
			int [] coordIndex = ifs.getCoordIndex();
			if (coordIndex.length > 700) {
				System.err.println("coordIndex "+coordIndex.length);
				int [] texCoordIndex = ifs.getTexCoordIndex();
				System.err.println("texCoordIndex "+texCoordIndex.length);
			}
			if (!transform(coord, translation)) {
				System.err.println("Unpacking coord in IndexedFaceSet failed");
			}
		} else if (node instanceof Appearance) {
		} else if (node instanceof Coordinate) {
			Coordinate coordinate = (Coordinate)node;
			float [] point = coordinate.getPoint();
			// System.err.println("point "+point.length);
			for (int p = 0; p < point.length; p += 3) {
				transform(point, p, translation);
			}
			coordinate.setPoint(point);
		} else if (node != null) {
			System.err.println("Unhandled class is "+node.getClass().getName());
		} else {
			System.err.println("Node is "+node);
			return false;
		}
		if (children != null) {
			for (int ch = 0; ch < children.size(); ch++) {
				X3DNode child = children.get(ch);
				if (!transform(child, translation)) {
					System.err.println("Unpacking child in "+node.getClass().getName()+" failed");
				}
			}
		}
		return true;
	}
}
