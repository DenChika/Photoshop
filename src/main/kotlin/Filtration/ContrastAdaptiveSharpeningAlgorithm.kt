package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IFiltrationAlgorithm
import kotlin.math.sqrt

class ContrastAdaptiveSharpeningAlgorithm : IFiltrationAlgorithm {
    private fun lerp(a: Float, b: Float, t: Float): Float {
        return a + (b - a) * t
    }
    private fun updateCoordinates(x: Int, y: Int): Pair<Int, Int> {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        var newX = x
        var newY = y
        if (newX < 0) {
            newX = 0
        }
        if (newX > width - 1) {
            newX = width - 1
        }
        if (newY < 0) {
            newY = 0
        }
        if (newY > height - 1) {
            newY = height - 1
        }
        return Pair(newX, newY)
    }
    private fun calculateMinMax(pixels: Array<Float>): Pair<Float, Float> {
        val min = minOf(pixels[1], pixels[4], pixels[7], pixels[3], pixels[5])
        val max = maxOf(pixels[1], pixels[4], pixels[7], pixels[3], pixels[5])
        val min2 = minOf(pixels[0], pixels[2], pixels[6], pixels[8], min)
        val max2 = maxOf(pixels[0], pixels[2], pixels[6], pixels[8], max)
        return Pair((min + min2) / 2, (max + max2) / 2)
    }
    private fun calculateWeights(pixels: Array<ColorSpaceInstance>): Triple<Array<Float>, Array<Float>, Array<Float>> {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val sharpness = AppConfiguration.Filtration.sharpnessValue.value
        val weightsR = Array(width * height) { 0.0f }
        val weightsG = Array(width * height) { 0.0f }
        val weightsB = Array(width * height) { 0.0f }
        var ind = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                var up = y - 1
                var down = y + 1
                var left = x - 1
                var right = x + 1
                if (up < 0)
                    up = 0
                if (down > height - 1)
                    down = height - 1
                if (left < 0)
                    left = 0
                if (right > width - 1)
                    right = width - 1

                val (cR, cG, cB) = pixels[y * width + x].GetFloatArrayOfValues()
                val (aR, aG, aB) = pixels[up * width + x].GetFloatArrayOfValues()
                val (bR, bG, bB) = pixels[y * width + left].GetFloatArrayOfValues()
                val (eR, eG, eB) = pixels[down * width + x].GetFloatArrayOfValues()
                val (dR, dG, dB) = pixels[y * width + right].GetFloatArrayOfValues()
                val (fR, fG, fB) = pixels[up * width + left].GetFloatArrayOfValues()
                val (gR, gG, gB) = pixels[up * width + right].GetFloatArrayOfValues()
                val (hR, hG, hB) = pixels[down * width + left].GetFloatArrayOfValues()
                val (iR, iG, iB) = pixels[down * width + right].GetFloatArrayOfValues()

                val (minR, maxR) = calculateMinMax(arrayOf(fR, aR, gR, bR, cR, dR, hR, eR, iR))
                val (minG, maxG) = calculateMinMax(arrayOf(fG, aG, gG, bG, cG, dG, hG, eG, iG))
                val (minB, maxB) = calculateMinMax(arrayOf(fB, aB, gB, bB, cB, dB, hB, eB, iB))

                val dMinR = minR; val dMaxR = 1.0f - maxR
                val dMinG = minG; val dMaxG = 1.0f - maxG
                val dMinB = minB; val dMaxB = 1.0f - maxB

                val AR = sqrt(minOf(dMinR, dMaxR) / maxR)
                val AG = sqrt(minOf(dMinG, dMaxG) / maxG)
                val AB = sqrt(minOf(dMinB, dMaxB) / maxB)
                if (AR.isNaN()) {
                    weightsR[ind] = 0.0f
                } else {
                    weightsR[ind] = AR
                }
                if (AG.isNaN()) {
                    weightsG[ind] = 0.0f
                } else {
                    weightsG[ind] = AG
                }
                if (AB.isNaN()) {
                    weightsB[ind] = 0.0f
                } else {
                    weightsB[ind] = AB
                }
                val developerMaximum = lerp(-0.125f, -0.2f, sharpness)
                weightsR[ind] = AR * developerMaximum
                weightsG[ind] = AG * developerMaximum
                weightsB[ind] = AB * developerMaximum
                ind++
            }
        }
        return Triple(weightsR, weightsG, weightsB)
    }
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val (weightsR, weightsG, weightB) = calculateWeights(pixels)
        val extendedPixels = pixels.map{it.GetFloatArrayOfValues()}.toTypedArray()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val (bx, by) = updateCoordinates(x, y - 1)
                val (cx, cy) = updateCoordinates(x + 1, y - 1)
                val (ex, ey) = updateCoordinates(x - 1, y)
                val (ix, iy) = updateCoordinates(x - 1, y + 1)
                val (hx, hy) = updateCoordinates(x + 2, y)
                val (lx, ly) = updateCoordinates(x + 2, y + 1)
                val (nx, ny) = updateCoordinates(x, y + 2)
                val (ox, oy) = updateCoordinates(x + 1, y + 2)
                val (gx, gy) = updateCoordinates(x + 1, y)
                val (jx, jy) = updateCoordinates(x, y + 1)
                val (kx, ky) = updateCoordinates(x + 1, y + 1)
                val (ax, ay) = updateCoordinates(x - 1, y - 1)
                val (dx, dy) = updateCoordinates(x + 2, y - 1)
                val (mx, my) = updateCoordinates(x - 1, y + 2)
                val (px, py) = updateCoordinates(x + 2, y + 2)

                val (fR, fG, fB) = extendedPixels[y * width + x]
                val (bR, bG, bB) = extendedPixels[by * width + bx]
                val (cR, cG, cB) = extendedPixels[cy * width + cx]
                val (eR, eG, eB) = extendedPixels[ey * width + ex]
                val (iR, iG, iB) = extendedPixels[iy * width + ix]
                val (hR, hG, hB) = extendedPixels[hy * width + hx]
                val (lR, lG, lB) = extendedPixels[ly * width + lx]
                val (nR, nG, nB) = extendedPixels[ny * width + nx]
                val (oR, oG, oB) = extendedPixels[oy * width + ox]
                val (gR, gG, gB) = extendedPixels[gy * width + gx]
                val (jR, jG, jB) = extendedPixels[jy * width + jx]
                val (kR, kG, kB) = extendedPixels[ky * width + kx]
                val (aR, aG, aB) = extendedPixels[ay * width + ax]
                val (dR, dG, dB) = extendedPixels[dy * width + dx]
                val (mR, mG, mB) = extendedPixels[my * width + mx]
                val (pR, pG, pB) = extendedPixels[py * width + px]

                val ppx = x / width.toFloat()
                val ppy = y / height.toFloat()
                var sR = (1.0f - ppx) * (1.0f - ppy)
                var (sG, sB) = Pair(sR, sR)
                var tR = ppx * (1.0f - ppy)
                var (tG, tB) = Pair(tR, tR)
                var uR = (1.0f - ppx) * ppy
                var (uG, uB) = Pair(uR, uR)
                var vR = ppx * ppy
                var (vG, vB) = Pair(vR, vR)

                val (fMinR, fMaxR) = calculateMinMax(arrayOf(aR, bR, cR, eR, fR, gR, iR, jR, kR))
                val (fMinG, fMaxG) = calculateMinMax(arrayOf(aG, bG, cG, eG, fG, gG, iG, jG, kG))
                val (fMinB, fMaxB) = calculateMinMax(arrayOf(aB, bB, cB, eB, fB, gB, iB, jB, kB))

                val (gMinR, gMaxR) = calculateMinMax(arrayOf(bR, cR, dR, fR, gR, hR, jR, kR, lR))
                val (gMinG, gMaxG) = calculateMinMax(arrayOf(bG, cG, dG, fG, gG, hG, jG, kG, lG))
                val (gMinB, gMaxB) = calculateMinMax(arrayOf(bB, cB, dB, fB, gB, hB, jB, kB, lB))

                val (jMinR, jMaxR) = calculateMinMax(arrayOf(eR, fR, gR, iR, jR, kR, mR, nR, oR))
                val (jMinG, jMaxG) = calculateMinMax(arrayOf(eG, fG, gG, iG, jG, kG, mG, nG, oG))
                val (jMinB, jMaxB) = calculateMinMax(arrayOf(eB, fB, gB, iB, jB, kB, mB, nB, oB))

                val (kMinR, kMaxR) = calculateMinMax(arrayOf(fR, gR, hR, jR, kR, lR, nR, oR, pR))
                val (kMinG, kMaxG) = calculateMinMax(arrayOf(fG, gG, hG, jG, kG, lG, nG, oG, pG))
                val (kMinB, kMaxB) = calculateMinMax(arrayOf(fB, gB, hB, jB, kB, lB, nB, oB, pB))

                val fContrastWeightR = 1 / (1.0f / 32 + (fMaxR - fMinR))
                val gContrastWeightR = 1 / (1.0f / 32 + (gMaxR - gMinR))
                val jContrastWeightR = 1 / (1.0f / 32 + (jMaxR - jMinR))
                val kContrastWeightR = 1 / (1.0f / 32 + (kMaxR - kMinR))

                val fContrastWeightG = 1 / (1.0f / 32 + (fMaxG - fMinG))
                val gContrastWeightG = 1 / (1.0f / 32 + (gMaxG - gMinG))
                val jContrastWeightG = 1 / (1.0f / 32 + (jMaxG - jMinG))
                val kContrastWeightG = 1 / (1.0f / 32 + (kMaxG - kMinG))

                val fContrastWeightB = 1 / (1.0f / 32 + (fMaxB - fMinB))
                val gContrastWeightB = 1 / (1.0f / 32 + (gMaxB - gMinB))
                val jContrastWeightB = 1 / (1.0f / 32 + (jMaxB - jMinB))
                val kContrastWeightB = 1 / (1.0f / 32 + (kMaxB - kMinB))

                sR *= fContrastWeightR; tR *= gContrastWeightR; uR *= jContrastWeightR; vR *= kContrastWeightR
                sG *= fContrastWeightG; tG *= gContrastWeightG; uG *= jContrastWeightG; vG *= kContrastWeightG
                sB *= fContrastWeightB; tB *= gContrastWeightB; uB *= jContrastWeightB; vB *= kContrastWeightB

                val (wfR, wfG, wfB) = Triple(weightsR[y * width + x], weightsG[y * width + x], weightB[y * width + x])
                val (wgR, wgG, wgB) = Triple(weightsR[gy * width + gx], weightsG[gy * width + gx], weightB[gy * width + gx])
                val (wjR, wjG, wjB) = Triple(weightsR[jy * width + jx], weightsG[jy * width + jx], weightB[jy * width + jx])
                val (wkR, wkG, wkB) = Triple(weightsR[ky * width + kx], weightsG[ky * width + kx], weightB[ky * width + kx])

                val WR = 2 * wfR * sR + 2 * wgR * tR + 2 * wjR * uR + 2 * wkR * vR +
                        (wgR * tR + wjR * uR + sR) + (wfR * sR + wkR * vR + tR) +
                        (wfR * sR + wkR * vR + uR) + (wjR * uR + wgR * tR + vR)
                val WG = 2 * wfG * sG + 2 * wgG * tG + 2 * wjG * uG + 2 * wkG * vG +
                        (wgG * tG + wjG * uG + sG) + (wfG * sG + wkG * vG + tG) +
                        (wfG * sG + wkG * vG + uG) + (wjG * uG + wgG * tG + vG)
                val WB = 2 * wfB * sB + 2 * wgB * tB + 2 * wjB * uB + 2 * wkB * vB +
                        (wgB * tB + wjB * uB + sB) + (wfB * sB + wkB * vB + tB) +
                        (wfB * sB + wkB * vB + uB) + (wjB * uB + wgB * tB + vB)

                var newR = (bR * wfR * sR + cR * wgR * tR + eR * wfR * sR + iR * wjR * uR + hR * wgR * tR + lR * wkR * vR +
                        nR * wjR * uR + oR * wkR * vR + fR * (wgR * tR + wjR * uR + sR) + gR * (wfR * sR + wkR * vR + tR) +
                        jR * (wfR * sR + wkR * vR + uR) + kR * (wjR * uR + wgR * tR + vR)) / WR
                var newG = (bG * wfG * sG + cG * wgG * tG + eG * wfG * sG + iG * wjG * uG + hG * wgG * tG + lG * wkG * vG +
                        nG * wjG * uG + oG * wkG * vG + fG * (wgG * tG + wjG * uG + sG) + gG * (wfG * sG + wkG * vG + tG) +
                        jG * (wfG * sG + wkG * vG + uG) + kG * (wjG * uG + wgG * tG + vG)) / WG
                var newB = (bB * wfB * sB + cB * wgB * tB + eB * wfB * sB + iB * wjB * uB + hB * wgB * tB + lB * wkB * vB +
                        nB * wjB * uB + oB * wkB * vB + fB * (wgB * tB + wjB * uB + sB) + gB * (wfB * sB + wkB * vB + tB) +
                        jB * (wfB * sB + wkB * vB + uB) + kB * (wjB * uB + wgB * tB + vB)) / WB

                if (newR.isNaN()) newR = 0f
                if (newG.isNaN()) newG = 0f
                if (newB.isNaN()) newB = 0f

                newR = newR.coerceIn(0.0f, 1.0f)
                newG = newG.coerceIn(0.0f, 1.0f)
                newB = newB.coerceIn(0.0f, 1.0f)
                pixels[y * width + x].UpdateValues(floatArrayOf(newR, newG, newB))
            }
        }
    }
}