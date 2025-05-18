/**
 * Copyright (c) 2022. John Carlson
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of content nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE

*/

import org.w3c.dom.*;
import org.w3c.dom.ls.*;
import javax.json.*;
import java.util.*;
import java.io.*;
import java.io.FileWriter;
import javax.xml.parsers.*;

public class X3DJSONLD {
	private boolean x3dTidy = false;
	private HashMap<String, JsonObject> protos = new HashMap<String, JsonObject>();
	private HashSet<String> builtins = new HashSet<String>();
	public String stripQuotes(String value) {
		if (value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
			return value.substring(1, value.length()-1);
		} else {
			return value;
		}
	} 
	public X3DJSONLD() {
		builtins.add("X3DAppearanceChildNode");
		builtins.add("X3DAppearanceNode");
		builtins.add("X3DArrayField");
		builtins.add("X3DBackgroundNode");
		builtins.add("X3DBindableNode");
		builtins.add("X3DChaserNode");
		builtins.add("X3DChildNode");
		builtins.add("X3DColorNode");
		builtins.add("X3DComposableVolumeRenderStyleNode");
		builtins.add("X3DComposedGeometryNode");
		builtins.add("X3DCoordinateNode");
		builtins.add("X3DDamperNode");
		builtins.add("X3DDragSensorNode");
		builtins.add("X3DEnvironmentalSensorNode");
		builtins.add("X3DEnvironmentTextureNode");
		builtins.add("X3DField");
		builtins.add("X3DFollowerNode");
		builtins.add("X3DFontStyleNode");
		builtins.add("X3DGeometricPropertyNode");
		builtins.add("X3DGeometryNode");
		builtins.add("X3DGroupingNode");
		builtins.add("X3DInfoNode");
		builtins.add("X3DInterpolatorNode");
		builtins.add("X3DKeyDeviceSensorNode");
		builtins.add("X3DLayerNode");
		builtins.add("X3DLayoutNode");
		builtins.add("X3DLightNode");
		builtins.add("X3DMaterialNode");
		builtins.add("X3DNBodyCollidableNode");
		builtins.add("X3DNBodyCollisionSpaceNode");
		builtins.add("X3DNetworkSensorNode");
		builtins.add("X3DNode");
		builtins.add("X3DNormalNode");
		builtins.add("X3DNurbsControlCurveNode");
		builtins.add("X3DNurbsSurfaceGeometryNode");
		builtins.add("X3DOneSidedMaterialNode");
		builtins.add("X3DParametricGeometryNode");
		builtins.add("X3DParticleEmitterNode");
		builtins.add("X3DParticlePhysicsModelNode");
		builtins.add("X3DPickSensorNode");
		builtins.add("X3DPointingDeviceSensorNode");
		builtins.add("X3DProductStructureChildNode");
		builtins.add("X3DPrototypeInstance");
		builtins.add("X3DRigidJointNode");
		builtins.add("X3DScriptNode");
		builtins.add("X3DSensorNode");
		builtins.add("X3DSequencerNode");
		builtins.add("X3DShaderNode");
		builtins.add("X3DShapeNode");
		builtins.add("X3DSingleTextureCoordinateNode");
		builtins.add("X3DSingleTextureNode");
		builtins.add("X3DSingleTextureTransformNode");
		builtins.add("X3DSoundChannelNode");
		builtins.add("X3DSoundDestinationNode");
		builtins.add("X3DSoundNode");
		builtins.add("X3DSoundProcessingNode");
		builtins.add("X3DSoundSourceNode");
		builtins.add("X3DStatement");
		builtins.add("X3DTexture2DNode");
		builtins.add("X3DTexture3DNode");
		builtins.add("X3DTextureCoordinateNode");
		builtins.add("X3DTextureNode");
		builtins.add("X3DTextureProjectorNode");
		builtins.add("X3DTextureTransformNode");
		builtins.add("X3DTimeDependentNode");
		builtins.add("X3DTouchSensorNode");
		builtins.add("X3DTriggerNode");
		builtins.add("X3DVertexAttributeNode");
		builtins.add("X3DViewpointNode");
		builtins.add("X3DViewportNode");
		builtins.add("X3DVolumeDataNode");
		builtins.add("X3DVolumeRenderStyleNode");
		builtins.add("X3DMaterialExtensionNode");
		builtins.add("X3DBoundedObject");
		builtins.add("X3DFogObject");
		builtins.add("X3DMetadataObject");
		builtins.add("X3DPickableObject");
		builtins.add("X3DProgrammableShaderObject");
		builtins.add("X3DUrlObject");
		builtins.add("AcousticProperties");
		builtins.add("Analyser");
		builtins.add("Anchor");
		builtins.add("Appearance");
		builtins.add("Arc2D");
		builtins.add("ArcClose2D");
		builtins.add("AudioClip");
		builtins.add("AudioDestination");
		builtins.add("Background");
		builtins.add("BallJoint");
		builtins.add("Billboard");
		builtins.add("BiquadFilter");
		builtins.add("BlendedVolumeStyle");
		builtins.add("BooleanFilter");
		builtins.add("BooleanSequencer");
		builtins.add("BooleanToggle");
		builtins.add("BooleanTrigger");
		builtins.add("BoundaryEnhancementVolumeStyle");
		builtins.add("BoundedPhysicsModel");
		builtins.add("Box");
		builtins.add("BufferAudioSource");
		builtins.add("CADAssembly");
		builtins.add("CADFace");
		builtins.add("CADLayer");
		builtins.add("CADPart");
		builtins.add("CartoonVolumeStyle");
		builtins.add("ChannelMerger");
		builtins.add("ChannelSelector");
		builtins.add("ChannelSplitter");
		builtins.add("Circle2D");
		builtins.add("ClipPlane");
		builtins.add("CollidableOffset");
		builtins.add("CollidableShape");
		builtins.add("Collision");
		builtins.add("CollisionCollection");
		builtins.add("CollisionSensor");
		builtins.add("CollisionSpace");
		builtins.add("Color");
		builtins.add("ColorChaser");
		builtins.add("ColorDamper");
		builtins.add("ColorInterpolator");
		builtins.add("ColorRGBA");
		builtins.add("ComposedCubeMapTexture");
		builtins.add("ComposedShader");
		builtins.add("ComposedTexture3D");
		builtins.add("ComposedVolumeStyle");
		builtins.add("Cone");
		builtins.add("ConeEmitter");
		builtins.add("Contact");
		builtins.add("Contour2D");
		builtins.add("ContourPolyline2D");
		builtins.add("Convolver");
		builtins.add("Coordinate");
		builtins.add("CoordinateChaser");
		builtins.add("CoordinateDamper");
		builtins.add("CoordinateDouble");
		builtins.add("CoordinateInterpolator");
		builtins.add("CoordinateInterpolator2D");
		builtins.add("Cylinder");
		builtins.add("CylinderSensor");
		builtins.add("Delay");
		builtins.add("DirectionalLight");
		builtins.add("DISEntityManager");
		builtins.add("DISEntityTypeMapping");
		builtins.add("Disk2D");
		builtins.add("DoubleAxisHingeJoint");
		builtins.add("DynamicsCompressor");
		builtins.add("EaseInEaseOut");
		builtins.add("EdgeEnhancementVolumeStyle");
		builtins.add("ElevationGrid");
		builtins.add("EspduTransform");
		builtins.add("ExplosionEmitter");
		builtins.add("Extrusion");
		builtins.add("FillProperties");
		builtins.add("FloatVertexAttribute");
		builtins.add("Fog");
		builtins.add("FogCoordinate");
		builtins.add("FontStyle");
		builtins.add("ForcePhysicsModel");
		builtins.add("Gain");
		builtins.add("GeneratedCubeMapTexture");
		builtins.add("GeoCoordinate");
		builtins.add("GeoElevationGrid");
		builtins.add("GeoLocation");
		builtins.add("GeoLOD");
		builtins.add("GeoMetadata");
		builtins.add("GeoOrigin");
		builtins.add("GeoPositionInterpolator");
		builtins.add("GeoProximitySensor");
		builtins.add("GeoTouchSensor");
		builtins.add("GeoTransform");
		builtins.add("GeoViewpoint");
		builtins.add("Group");
		builtins.add("HAnimDisplacer");
		builtins.add("HAnimHumanoid");
		builtins.add("HAnimJoint");
		builtins.add("HAnimMotion");
		builtins.add("HAnimSegment");
		builtins.add("HAnimSite");
		builtins.add("ImageCubeMapTexture");
		builtins.add("ImageTexture");
		builtins.add("ImageTexture3D");
		builtins.add("IndexedFaceSet");
		builtins.add("IndexedLineSet");
		builtins.add("IndexedQuadSet");
		builtins.add("IndexedTriangleFanSet");
		builtins.add("IndexedTriangleSet");
		builtins.add("IndexedTriangleStripSet");
		builtins.add("Inline");
		builtins.add("IntegerSequencer");
		builtins.add("IntegerTrigger");
		builtins.add("IsoSurfaceVolumeData");
		builtins.add("KeySensor");
		builtins.add("Layer");
		builtins.add("LayerSet");
		builtins.add("Layout");
		builtins.add("LayoutGroup");
		builtins.add("LayoutLayer");
		builtins.add("LinePickSensor");
		builtins.add("LineProperties");
		builtins.add("LineSet");
		builtins.add("ListenerPointSource");
		builtins.add("LoadSensor");
		builtins.add("LocalFog");
		builtins.add("LOD");
		builtins.add("Material");
		builtins.add("Matrix3VertexAttribute");
		builtins.add("Matrix4VertexAttribute");
		builtins.add("MetadataBoolean");
		builtins.add("MetadataDouble");
		builtins.add("MetadataFloat");
		builtins.add("MetadataInteger");
		builtins.add("MetadataSet");
		builtins.add("MetadataString");
		builtins.add("MicrophoneSource");
		builtins.add("MotorJoint");
		builtins.add("MovieTexture");
		builtins.add("MultiTexture");
		builtins.add("MultiTextureCoordinate");
		builtins.add("MultiTextureTransform");
		builtins.add("NavigationInfo");
		builtins.add("Normal");
		builtins.add("NormalInterpolator");
		builtins.add("NurbsCurve");
		builtins.add("NurbsCurve2D");
		builtins.add("NurbsOrientationInterpolator");
		builtins.add("NurbsPatchSurface");
		builtins.add("NurbsPositionInterpolator");
		builtins.add("NurbsSet");
		builtins.add("NurbsSurfaceInterpolator");
		builtins.add("NurbsSweptSurface");
		builtins.add("NurbsSwungSurface");
		builtins.add("NurbsTextureCoordinate");
		builtins.add("NurbsTrimmedSurface");
		builtins.add("OpacityMapVolumeStyle");
		builtins.add("OrientationChaser");
		builtins.add("OrientationDamper");
		builtins.add("OrientationInterpolator");
		builtins.add("OrthoViewpoint");
		builtins.add("OscillatorSource");
		builtins.add("PackagedShader");
		builtins.add("ParticleSystem");
		builtins.add("PeriodicWave");
		builtins.add("PhysicalMaterial");
		builtins.add("PickableGroup");
		builtins.add("PixelTexture");
		builtins.add("PixelTexture3D");
		builtins.add("PlaneSensor");
		builtins.add("PointEmitter");
		builtins.add("PointLight");
		builtins.add("PointPickSensor");
		builtins.add("PointProperties");
		builtins.add("PointSet");
		builtins.add("Polyline2D");
		builtins.add("PolylineEmitter");
		builtins.add("Polypoint2D");
		builtins.add("PositionChaser");
		builtins.add("PositionChaser2D");
		builtins.add("PositionDamper");
		builtins.add("PositionDamper2D");
		builtins.add("PositionInterpolator");
		builtins.add("PositionInterpolator2D");
		builtins.add("PrimitivePickSensor");
		builtins.add("ProgramShader");
		builtins.add("ProjectionVolumeStyle");
		builtins.add("ProtoInstance");
		builtins.add("ProximitySensor");
		builtins.add("QuadSet");
		builtins.add("ReceiverPdu");
		builtins.add("Rectangle2D");
		builtins.add("RigidBody");
		builtins.add("RigidBodyCollection");
		builtins.add("ScalarChaser");
		builtins.add("ScalarDamper");
		builtins.add("ScalarInterpolator");
		builtins.add("ScreenFontStyle");
		builtins.add("ScreenGroup");
		builtins.add("Script");
		builtins.add("SegmentedVolumeData");
		builtins.add("ShadedVolumeStyle");
		builtins.add("ShaderPart");
		builtins.add("ShaderProgram");
		builtins.add("Shape");
		builtins.add("SignalPdu");
		builtins.add("SilhouetteEnhancementVolumeStyle");
		builtins.add("SingleAxisHingeJoint");
		builtins.add("SliderJoint");
		builtins.add("Sound");
		builtins.add("SpatialSound");
		builtins.add("Sphere");
		builtins.add("SphereSensor");
		builtins.add("SplinePositionInterpolator");
		builtins.add("SplinePositionInterpolator2D");
		builtins.add("SplineScalarInterpolator");
		builtins.add("SpotLight");
		builtins.add("SquadOrientationInterpolator");
		builtins.add("StaticGroup");
		builtins.add("StreamAudioDestination");
		builtins.add("StreamAudioSource");
		builtins.add("StringSensor");
		builtins.add("SurfaceEmitter");
		builtins.add("Switch");
		builtins.add("TexCoordChaser2D");
		builtins.add("TexCoordDamper2D");
		builtins.add("Text");
		builtins.add("TextureBackground");
		builtins.add("TextureCoordinate");
		builtins.add("TextureCoordinate3D");
		builtins.add("TextureCoordinate4D");
		builtins.add("TextureCoordinateGenerator");
		builtins.add("TextureProjector");
		builtins.add("TextureProjectorParallel");
		builtins.add("TextureProperties");
		builtins.add("TextureTransform");
		builtins.add("TextureTransform3D");
		builtins.add("TextureTransformMatrix3D");
		builtins.add("TimeSensor");
		builtins.add("TimeTrigger");
		builtins.add("ToneMappedVolumeStyle");
		builtins.add("TouchSensor");
		builtins.add("Transform");
		builtins.add("TransformSensor");
		builtins.add("TransmitterPdu");
		builtins.add("TriangleFanSet");
		builtins.add("TriangleSet");
		builtins.add("TriangleSet2D");
		builtins.add("TriangleStripSet");
		builtins.add("TwoSidedMaterial");
		builtins.add("UniversalJoint");
		builtins.add("UnlitMaterial");
		builtins.add("Viewpoint");
		builtins.add("ViewpointGroup");
		builtins.add("Viewport");
		builtins.add("VisibilitySensor");
		builtins.add("VolumeData");
		builtins.add("VolumeEmitter");
		builtins.add("VolumePickSensor");
		builtins.add("WaveShaper");
		builtins.add("WindPhysicsModel");
		builtins.add("WorldInfo");
		builtins.add("EnvironmentLight");
		builtins.add("Tangent");
		builtins.add("ImageTextureAtlas");
		builtins.add("AnisotropyMaterialExtension");
		builtins.add("BlendMode");
		builtins.add("ClearcoatMaterialExtension");
		builtins.add("DepthMode");
		builtins.add("DispersionMaterialExtension");
		builtins.add("EmissiveStrengthMaterialExtension");
		builtins.add("IORMaterialExtension");
		builtins.add("InstancedShape");
		builtins.add("IridescenceMaterialExtension");
		builtins.add("SheenMaterialExtension");
		builtins.add("SpecularGlossinessMaterial");
		builtins.add("SpecularMaterialExtension");
		builtins.add("TransmissionMaterialExtension");
		builtins.add("VolumeMaterialExtension");
		builtins.add("DiffuseTransmissionMaterialExtension");
		builtins.add("component");
		builtins.add("connect");
		builtins.add("EXPORT");
		builtins.add("ExternProtoDeclare");
		builtins.add("field");
		builtins.add("fieldValue");
		builtins.add("head");
		builtins.add("IMPORT");
		builtins.add("IS");
		builtins.add("meta");
		builtins.add("ProtoBody");
		builtins.add("ProtoDeclare");
		builtins.add("ProtoInterface");
		builtins.add("ROUTE");
		builtins.add("Scene");
		builtins.add("unit");
		builtins.add("X3D");
		builtins.add("SFBool");
		builtins.add("MFBool");
		builtins.add("SFColor");
		builtins.add("MFColor");
		builtins.add("SFColorRGBA");
		builtins.add("MFColorRGBA");
		builtins.add("SFDouble");
		builtins.add("MFDouble");
		builtins.add("SFFloat");
		builtins.add("MFFloat");
		builtins.add("SFImage");
		builtins.add("MFImage");
		builtins.add("SFInt32");
		builtins.add("MFInt32");
		builtins.add("SFMatrix3d");
		builtins.add("MFMatrix3d");
		builtins.add("SFMatrix3f");
		builtins.add("MFMatrix3f");
		builtins.add("SFMatrix4d");
		builtins.add("MFMatrix4d");
		builtins.add("SFMatrix4f");
		builtins.add("MFMatrix4f");
		builtins.add("SFNode");
		builtins.add("MFNode");
		builtins.add("SFRotation");
		builtins.add("MFRotation");
		builtins.add("SFString");
		builtins.add("MFString");
		builtins.add("SFTime");
		builtins.add("MFTime");
		builtins.add("SFVec2d");
		builtins.add("MFVec2d");
		builtins.add("SFVec2f");
		builtins.add("MFVec2f");
		builtins.add("SFVec3d");
		builtins.add("MFVec3d");
		builtins.add("SFVec3f");
		builtins.add("MFVec3f");
		builtins.add("SFVec4d");
		builtins.add("MFVec4d");
		builtins.add("SFVec4f");
		builtins.add("MFVec4f");
	}
	public void elementSetAttribute(Element element, String key, List<JsonValue> value, Document document) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.size(); i++) {
			if (i > 0) {
				sb.append(" ");
			}
			sb.append(value.get(i));
		}
		if (key.equals("name")) {
			element.setAttribute(key, sb.toString());
		} else if (element.getNodeName().equals("ProtoInstance") && !key.equals("DEF") && !key.equals("name")) {
			Element fieldValue = document.createElement("fieldValue");
			fieldValue.setAttribute("name", key);
			fieldValue.setAttribute("value", sb.toString());
			element.appendChild(fieldValue);
		} else {
			element.setAttribute(key, sb.toString());
		}
	}
	public void elementSetAttribute(Element element, String key, String value, Document document) {
		if (element.getNodeName().equals("ProtoInstance") && !key.equals("DEF") && !key.equals("name")) {
			Element fieldValue = document.createElement("fieldValue");
			fieldValue.setAttribute("name", key);
			fieldValue.setAttribute("value", value);
			element.appendChild(fieldValue);
		} else if (key.equals("SON schema")) {
			// JSON Schema
		} else if (key.equals("ncoding")) {
			// encoding, UTF-8
		} else if (value == null) {
			element.setAttribute(key, null);
		} else {
			// System.err.println(key+"= SA "+value);
			element.setAttribute(key, stripQuotes(value));
		}
	}

	public Element CreateElement(Document document, String key, String containerField, JsonObject object) {
		if (key.equals("ProtoDeclare") || key.equals("ExternProtoDeclare")) {
			if (object.get("@name") != null) {
				String name = stripQuotes(object.get("@name").toString());
				if (name != null) {
					if (builtins.contains(name)) {
						System.err.println("Attempt to override builtin name '"+name+"' rejected");
				
					} else if (protos.get(name) != null) {
						System.err.println("Attempt to override PROTO name '"+name+"' rejected");

					} else {
						System.err.println("PROTO name "+name);
						protos.put(name, object);
					}
				}
			}
			if (object.get("@DEF") != null) {
				String DEF = stripQuotes(object.get("@DEF").toString());
				if (DEF != null) {
					if (builtins.contains(DEF)) {
						System.err.println("Attempt to override builtin name '"+DEF+"' rejected");
					} else if (protos.get(DEF) != null) {
						System.err.println("Attempt to override PROTO DEF '"+DEF+"' rejected");
					} else {
						System.err.println("PROTO DEF "+DEF);
						protos.put(DEF, object);
					}
				}
			}
		}
		JsonObject new_object = protos.get(key);
		Element child = null;
		if (new_object != null) {
			String new_key = "ProtoInstance";
			child = document.createElement(new_key);
			// System.err.println("Creating "+new_key);
			child.setAttribute("name", key);
		} else {
			child = document.createElement(key);
		}
		if (containerField != null &&
				((containerField.equals("geometry")  && key.equals("IndexedFaceSet")) ||
				 (containerField.equals("geometry")  && key.equals("Text")) ||
				 (containerField.equals("geometry")  && key.equals("IndexedTriangleSet")) ||
				 (containerField.equals("geometry")  && key.equals("Sphere")) ||
				 (containerField.equals("geometry")  && key.equals("Cylinder")) ||
				 (containerField.equals("geometry")  && key.equals("Cone")) ||
				 (containerField.equals("geometry")  && key.equals("LineSet")) ||
				 (containerField.equals("geometry")  && key.equals("IndexedLineSet")) ||
				 (containerField.equals("geometry")  && key.equals("Box")) ||
				 (containerField.equals("geometry")  && key.equals("Extrusion")) ||
				 (containerField.equals("geometry")  && key.equals("GeoElevationGrid")) ||
				 (containerField.equals("shape")  && key.equals("Shape")) ||
				 (containerField.equals("skin")  && key.equals("Shape")) ||
				 (containerField.endsWith("exture")  && key.equals("ImageTexture")) ||
				 (key.equals("HAnimSegment")) ||
				 (key.equals("HAnimSite")) ||
				 (key.equals("HAnimMotion")) ||
				 (containerField.equals("skinCoord")  && key.equals("Coordinate")) || // overwrite coord with skinCoord, if set
				 (containerField.equals("skin")  && key.equals("IndexedFaceSet")) ||
				 ((containerField.equals("skinBindingCoords") || containerField.equals("skinCoord")) && key.equals("Coordinate")) ||
				 ((containerField.equals("normal") || containerField.equals("skinBindingNormals") || containerField.equals("skinNormal")) && key.equals("Normal")) ||
				 ((containerField.equals("skeleton") || containerField.equals("children") || containerField.equals("joints"))  && key.equals("HAnimJoint"))
				)) {
			elementSetAttribute(child, "containerField", containerField, document);
		}
		return child;
	}

	public void CDATACreateFunction(Document document, Element element, JsonArray value) {
		// System.err.println("GOT HERE IN CDATA");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.size(); i++) {
			if (i > 0) {
				sb.append("\n");
			}
			sb.append(value.get(i).toString()
			// .replaceAll("&#xD;", "")
			.replaceAll("^\"", "")
			.replaceAll("\\\\t", "\t")
			.replaceAll("\"$", "")
			.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replaceAll("&amp;", "&")
			.replaceAll("&quot;", "\""));
			// .replaceAll("'([^'\r\n]*)\n([^']*)'", "'$1\\r\\n$2'")
		}
		String str = sb.toString();
		CDATASection cdata = document.createCDATASection(str);
		element.appendChild(cdata);
	}


	public void convertProperty(Document document, String key, JsonObject object, Element element, String containerField) {
		// System.err.println(key+"= P "+object.get(key));
		if (object != null && object.get(key) instanceof JsonObject) {
			if (key.equals("@sourceCode")) {
				// System.err.println("FOUND SOURCE 1");
				CDATACreateFunction(document, element, (JsonArray)object.get(key));
			} else if (key.substring(0,1).equals("@")) {
				convertJsonValue(document, object.get(key), key, element, containerField);
			} else if (key.substring(0,1).equals("-")) {
				// System.err.println("converting children at "+key);
				convertJsonValue(document, object.get(key), key, element, key.substring(1));
			} else if (key.equals("#comment")) {
				if (object.get(key) instanceof JsonArray) {
					JsonArray array = (JsonArray)object.get(key);
					for (int childkey = 0; childkey <  array.size(); childkey++) {  // for each field
						Comment child = document.createComment(CommentStringToXML(array.get(childkey).toString()));
						element.appendChild(child);
					}
				} else {
						Comment child = document.createComment(CommentStringToXML(object.get(key).toString()));
						element.appendChild(child);
				}
			} else if (key.equals("#sourceCode")) {
				// System.err.println("FOUND SOURCE 2");
				CDATACreateFunction(document, element, (JsonArray)object.get(key));
			} else if (key.equals("connect") || key.equals("fieldValue") || key.equals("field") || key.equals("meta") || key.equals("component") || key.equals("unit")) {
				JsonArray array = (JsonArray)object.get(key);
				convertJsonArray(document, array, key, element, containerField);
			} else {
				convertJsonValue(document, object.get(key), key, element, containerField);
			}
		}
	}

	public String CommentStringToXML(String str) {
		String y = str;
		// System.err.println("X3DJSONLD comment replacing "+ y);
		str = y;
		String x;
		do {
			x = str;
			str = x.replaceAll("(.*)\\\\\"(.*)\\\\\"(.*)", "$1\"$2\"$3");
		} while (!x.equals(str));
		do {
			x = str;
			str = x.replaceAll("(.*)\\\\\"(.*)", "$1\"$2");
		} while (!x.equals(str));
		do {
			x = str;
			str = x.replaceAll("\"\"", "\" \"");
		} while (!x.equals(str));
		if (!y.equals(str)) {
		// System.err.println("with                        "+ str);
		} else {
		// System.err.println("ok");
		}
		return str;
	}

	public String NavigationInfoTypeToXML(String str) {
		String y = str;
		System.err.println("X3DJSONLD jsonstring replacing "+ y);
		str = y.replaceAll("\\\\", "");
		if (!y.equals(str)) {
			System.err.println("with                           "+ str);
		} else {
			System.err.println("ok");
		}
		return str;
	}

	public String fixXML(String str, String version) {
		String y = str;
		// System.err.println("fixXML replacing "+ y);
		// str = str.replace("?>", "?>\n<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D "+version+"//EN\" \"https://www.web3d.org/specifications/x3d-"+version+".dtd\">");
		// str = str.replaceFirst("xsd:noNamespaceSchemaLocation=\"[^\"]*\"", "");
		if (!y.equals(str)) {
		// System.err.println("with             "+ str);
		} else {
		// System.err.println("ok");
		}
		return str;
	}

	public void convertJsonObject(Document document, JsonObject object, String parentkey, Element element, String containerField) {
		Boolean kii;
		try {
			Integer.parseInt(parentkey);
			kii = true;
		} catch (Exception e) {
			kii = false;
		}
		Element child;
		if (kii || parentkey.startsWith("-")) {
			child = element;
		} else {
			if ((containerField == null || containerField.equals("children")) && parentkey.equals("HAnimJoint") && element.getTagName().equals("HAnimHumanoid")) {
				containerField = "joints";
			}
			if ((containerField == null || containerField.equals("coord")) && parentkey.equals("Coordinate") && element.getTagName().equals("HAnimHumanoid")) {
				containerField = "skinCoord";
			}
			child = CreateElement(document, parentkey, containerField, object);
		}
		Iterator<String> keyiter = object.keySet().iterator();
		while (keyiter.hasNext()) {
			String key = keyiter.next();
			JsonValue ok = object.get(key);
			// System.err.println(key+"= O "+ok);
			if (ok instanceof JsonObject) {
				if (key.equals("@type") && parentkey.equals("NavigationInfo") && ok instanceof JsonString) {
					elementSetAttribute(child, key.substring(1), NavigationInfoTypeToXML(ok.toString()), document);
				} else if (key.substring(0,1).equals("@")) {
					convertProperty(document, key, (JsonObject)ok, child, containerField);
				} else if (key.substring(0,1).equals("-")) {
					convertJsonObject(document, (JsonObject)ok, key, child, key.substring(1));
				} else {
					convertJsonObject(document, (JsonObject)ok, key, child, containerField);
				}
			} else if (ok instanceof JsonArray) {
				convertJsonArray(document, (JsonArray)ok, key, child, containerField);
			} else if (ok instanceof JsonNumber) {
				elementSetAttribute(child, key.substring(1),ok.toString(), document);
			} else if (ok instanceof JsonString) {
				if (key.equals("#comment")) {
					Comment comment = document.createComment(CommentStringToXML(ok.toString()));
					child.appendChild(comment);
				} else if (key.equals("@type") && parentkey.equals("NavigationInfo")) {
					elementSetAttribute(child, key.substring(1), NavigationInfoTypeToXML(ok.toString()), document);
				} else {
					// ordinary string attributes
					elementSetAttribute(child, key.substring(1), ok.toString(), document);
				}
			} else if (ok == JsonValue.FALSE || ok == JsonValue.TRUE || ok == JsonValue.NULL) {
				elementSetAttribute(child, key.substring(1),ok.toString(), document);
			} else if (ok == null) {
			} else {
			}
		}
		if (!kii && !parentkey.startsWith("-")) {
			element.appendChild(child);
			// element.appendChild(document.createTextNode("\n"));
		}
	}

	public void convertJsonArray(Document document, JsonArray array, String parentkey, Element element, String containerField) {
		Boolean arrayOfStrings = false;
		List<JsonValue> localArray = new ArrayList<JsonValue>();
		Integer arraysize = array.size();
		if ("meta".equals(parentkey)) {
			arraysize = array.size() - (this.x3dTidy ? 2 : 3);  // skip meta statements added by X3dToJson.xslt and x3d-tidy
		}
		for (int key = 0; key < arraysize; key++) {
			JsonValue ok = array.get(key);
			// System.err.println(key+","+parentkey+"= A "+ok);
			if (ok instanceof JsonNumber) {
				localArray.add(ok);
			} else if (ok instanceof JsonString) {
				localArray.add(ok);
				arrayOfStrings = true;
			} else if (ok == JsonValue.TRUE || ok == JsonValue.FALSE || ok == JsonValue.NULL) {
				localArray.add(ok);
			} else if (ok instanceof JsonObject) {
				Boolean kii;
				try {
					Integer.parseInt(""+key);
					kii = true;
				} catch (Exception e) {
					kii = false;
				}
				if (!parentkey.startsWith("-") && kii) {
					convertJsonValue(document, ok, parentkey, element, containerField);
				} else {
					convertJsonValue(document, ok, ""+key, element, parentkey.substring(1));
				}
			} else if (ok instanceof JsonArray) {
				convertJsonValue(document, ok, ""+key, element, containerField);
			} else if (ok == null) {
			} else {
			}
		}
		if (parentkey.equals("@sourceCode")) {
			// System.err.println("FOUND SOURCE 3");
			CDATACreateFunction(document, element, array);
		} else if (parentkey.substring(0,1).equals("@")) {
			elementSetAttribute(element, parentkey.substring(1), localArray, document);
		} else if (parentkey.equals("#sourceCode")) {
			// System.err.println("FOUND SOURCE 4");
			CDATACreateFunction(document, element, array);
		}
	}

	public Element convertJsonValue(Document document, JsonValue object, String parentkey, Element element, String containerField) {
		// System.err.println(parentkey+"= V "+object);
		if (object instanceof JsonArray) {
			convertJsonArray(document, (JsonArray)object, parentkey, element, containerField);
		} else {
			convertJsonObject(document, (JsonObject)object, parentkey, element, containerField);
		}
		return element;
	}

	public Document loadJsonIntoDocument(JsonObject jsobj, String version, boolean x3dTidy) throws ParserConfigurationException {
		this.x3dTidy = x3dTidy;
		String unenversion = version.toString().replaceAll("%22", "").replaceAll("\"", "");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		Element element = CreateElement(document, "X3D", null, null);
		elementSetAttribute(element,  "xmlns:xsd",  "http://www.w3.org/2001/XMLSchema-instance", document);
		// elementSetAttribute(element,  "xsi:schemaLocation",  "https://www.web3d.org/specifications/x3d-"+unenversion+".xsd", document);
		// ((JsonObject)jsobj.get("X3D")).remove("xsd:noNamespaceSchemaLocation");
		convertJsonObject(document, (JsonObject)jsobj.get("X3D"), "-", element, null);
		// element.removeAttribute("xsd:noNamespaceSchemaLocation");
		// convertProperty(document, "X3D", (JsonObject)(jsobj.get("X3D")), element, null);
		document.appendChild(element);
		DOMImplementation domImplementation = db.getDOMImplementation();
		DocumentType doctype = domImplementation.createDocumentType("X3D", "ISO//Web3D//DTD X3D "+unenversion+"//EN", "https://www.web3d.org/specifications/x3d-"+unenversion+".dtd");
		document.insertBefore(doctype, element);
		return document;
	}

	public JsonObject readJsonFile(File jsonFile) throws FileNotFoundException {
		InputStream is = new FileInputStream(jsonFile);
		JsonReader reader = Json.createReader(is);
		JsonObject jsobj = reader.readObject();
		return jsobj;
	}

	public String getX3DVersion(JsonObject jsobj) {
		String version = "4.0";
		if (jsobj != null) {
			version = ((JsonObject)jsobj.get("X3D")).get("@version").toString();
		}
		return version.replaceAll("\"", "");
	}
	public static void main(String args[]) {
		try {
			X3DJSONLD loader = new X3DJSONLD();
			JsonObject jsobj = loader.readJsonFile(new File(args[0]));
			Document document = loader.loadJsonIntoDocument(jsobj, loader.getX3DVersion(jsobj), args[0].endsWith(".x3dj"));
			System.out.println(loader.serializeDOM(loader.getX3DVersion(jsobj), document));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String serializeDOM(String x3dVersion, Document document) {
		DOMImplementationLS ls = (DOMImplementationLS)document.getImplementation();
		LSOutput output = ls.createLSOutput();
		LSSerializer ser = ls.createLSSerializer();
        	ser.getDomConfig().setParameter("format-pretty-print", true);
		StringWriter writer = new StringWriter();
		output.setCharacterStream(writer);
		output.setEncoding("UTF-8");
		ser.write(document, output);
		String xml = writer.toString();
		// xml = fixXML(xml, x3dVersion);
		return xml;
	}
}
