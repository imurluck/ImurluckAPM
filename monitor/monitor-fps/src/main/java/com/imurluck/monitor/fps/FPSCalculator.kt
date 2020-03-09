package com.imurluck.monitor.fps


internal class FPSCalculator {

    private var frameStartTime = -1L

    private var lastFPSCollectTime = -1L

    private var framesInCollectTime = 0L

    var callback: Callback? = null

    fun onFrameStart() {
        frameStartTime = System.nanoTime()
    }

    fun onFrameEnd() {
        val frameEndTime = System.nanoTime()
        framesInCollectTime++
        //first time
        if (lastFPSCollectTime == -1L) {
            lastFPSCollectTime = frameEndTime
            return
        }
        //application probably not doing anything, onFrame method in Choreographer has't invoked over one second,
        //so, don't collect this time
        if (frameEndTime - lastFPSCollectTime >= NANOS_PER_SECONDS) {
            lastFPSCollectTime = frameEndTime
            framesInCollectTime = 0
            return
        }
        if (frameEndTime - lastFPSCollectTime >= FPS_COLLECT_INTERVAL_TIME) {
            val fps = NANOS_PER_SECONDS.toFloat() / (frameEndTime - lastFPSCollectTime) * framesInCollectTime
            callback?.onFPSCalculate((fps + 0.5F).toInt())
            lastFPSCollectTime = frameEndTime
            framesInCollectTime = 0
        }
    }

    companion object {

        private const val NANOS_PER_SECONDS = 60 * 16666666L

        /**
         * collect FPS info per 1/3 second with NANO unit
         */
        private const val FPS_COLLECT_INTERVAL_TIME = NANOS_PER_SECONDS / 3
    }

    internal interface Callback {

        fun onFPSCalculate(fps: Int)
    }
}