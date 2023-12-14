/**
 * Copyright (c) 2022-2023. John Carlson
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileWriter;
import org.web3d.x3d.jsail.X3DLoaderDOM;
import org.web3d.x3d.jsail.Core.X3D;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;

class MyDefaultHandler extends DefaultHandler {
	public void warning(SAXParseException e) {
		e.printStackTrace();
	}
	public void error(SAXParseException e) {
		e.printStackTrace();
	}
	public void fatalError(SAXParseException e) {
		e.printStackTrace();
	}
}

public class Validate {
	public static void main(String [] args) throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File("x3d-4.0.xsd"));
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new MyDefaultHandler());

		for (int i = 0; i < args.length; i++) {
			try {
				//if (args[i].contains(File.separator+"originals"+File.separator)) {
					//continue;
				//}
				X3DLoaderDOM xmlLoader = new X3DLoaderDOM();
				Document document;
				File f = new File(args[i]);
				System.out.println("Processing "+args[i]);
				if (args[i].endsWith(".json") || args[i].endsWith(".x3dj")) {
					X3DJSONLD loader = new X3DJSONLD();
					JsonObject jsobj = loader.readJsonFile(f);
					document = loader.loadJsonIntoDocument(jsobj);
					FileWriter fw = new FileWriter(args[i].substring(0, args[i].lastIndexOf("."))+".xml");
					fw.write((loader.serializeDOM(loader.getX3DVersion(jsobj), document)));
					fw.close();
				} else {
					document = db.parse(f);
				}

				// Validate with XML Schema
				DOMSource source = new DOMSource(document.getElementsByTagName("X3D").item(0));
				validator.validate(source);

				/*
				// Validate with X3DJSAIL
				X3D X3D0 = (X3D)xmlLoader.toX3dModelInstance(document);
				String validationResults = X3D0.validationReport();
				if (validationResults.startsWith("\n")) {
					System.out.println("Validation report for "+args[i]);
					System.out.println(validationResults.trim());
				} else {
					System.out.println("Valid "+args[i]);
				}
				*/
			} catch (Exception e) {
				System.out.println("Invalid "+args[i]);
				e.printStackTrace();
			}
		}
	}
}
