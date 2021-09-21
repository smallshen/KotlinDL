/*
 * Copyright 2020 JetBrains s.r.o. and Kotlin Deep Learning project contributors. All Rights Reserved.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */
package org.jetbrains.kotlinx.dl.dataset.preprocessor.image

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

/**
 * Interface for saving image preprocessing output result.
 */
public interface ImageSaver {
    public fun save(filename: String, image: BufferedImage)
}

/**
 * This [ImageSaver] allows saving image preprocessing result to file or directory in jpg format.
 *
 * @property [dirLocation] Could be link to the file or directory.
 *
 * NOTE: currently it supports [BufferedImage.TYPE_3BYTE_BGR] image type only.
 */
public class Save(
    public var dirLocation: File? = null
) : ImageSaver {
    @Throws(IOException::class)
    override fun save(filename: String, image: BufferedImage) {
        val outputFile: File = if (dirLocation!!.isDirectory) {
            File(dirLocation, filename)
        } else {
            dirLocation!!
        }
        // TODO: file extension is a part of name, need to extract name without extension
        ImageIO.write(image, "jpg", outputFile)
    }
}

/** */
public fun ImagePreprocessorBase.save(block: Save.() -> Unit) {
    save = Save().apply(block)
}