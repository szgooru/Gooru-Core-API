/////////////////////////////////////////////////////////////
// AssetManagerImpl.java
// gooru-api
// Created by Gooru on 2014
// Copyright (c) 2014 Gooru. All rights reserved.
// http://www.goorulearning.org/
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
/////////////////////////////////////////////////////////////
package org.ednovo.gooru.domain.service.resource.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.ednovo.gooru.core.api.model.Asset;
import org.ednovo.gooru.domain.service.resource.AssetManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service("assetManager")
public class AssetManagerImpl implements AssetManager {

	private final Logger logger = LoggerFactory.getLogger(AssetManagerImpl.class);

	@Override
	public void saveAssetResource(Asset asset, String realPath) throws Exception {
		this.addAsset(realPath, asset.getName(), asset.getFileData());
	}

	private void addAsset(String resourceURI, String fileName, byte[] fileData) throws Exception {

		logger.info("Adding asset: " + fileName + " with resourceURI: " + resourceURI);
		createPathIfNotExist(resourceURI);
		File file = new File(resourceURI + "/" + fileName);

		OutputStream out = new FileOutputStream(file);
		out.write(fileData);
		out.close();
	}

	private void createPathIfNotExist(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	@Override
	public void deletePathIfExist(String path) {
		File filePath = new File(path);
		if (filePath.exists()) {
			if (filePath.isDirectory()) {
				File[] files = filePath.listFiles();
				for (File file : files) {
					file.delete();
				}
			}
			filePath.delete();
		}
	}
}
