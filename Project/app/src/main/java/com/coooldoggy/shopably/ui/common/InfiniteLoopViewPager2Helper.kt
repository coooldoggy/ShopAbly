package com.coooldoggy.shopably.ui.common

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

class InfiniteLoopViewPager2Helper(val viewPager2: ViewPager2) {

    companion object{
        private val TAG = InfiniteLoopViewPager2Helper::class.java.simpleName
    }

    private var scrollAnimator: ValueAnimator? = null
    var onPageSelectedListener: OnPageSelectedListener? = null

    private var isAutoScroll = false

    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var vp2LoopHandler: Handler? = null

    fun init() {
        onPageChangeCallback = getOnPageChangeCallback(viewPager2)
        onPageChangeCallback?.let {
            viewPager2.registerOnPageChangeCallback(it)
        }

        vp2LoopHandler = getLoopHandler()
    }

    fun clear() {
        onPageSelectedListener = null
        scrollAnimator = null

        stopAutoScroll()
        vp2LoopHandler = null

        onPageChangeCallback?.let {
            viewPager2.unregisterOnPageChangeCallback(it)
        }
        onPageChangeCallback = null
    }

    private fun getOnPageChangeCallback(viewPager2: ViewPager2) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            //원본 아이템이 1개인 경우 -> 처리 안함
            //원본 아이템이 2개인 경우 -> A' B' A B A' B' 총 6개
            //원본 아이템이 3개인 경우 -> B' C' A B C A' B' 총 7개

            val internalAdapter = viewPager2.adapter ?: return

            if(internalAdapter.itemCount >= 6 && positionOffset == 0F) {
                when(position) {
                    0 -> {
                        scrollAnimator?.cancel()
                        if(!viewPager2.isFakeDragging) {
                            viewPager2.setCurrentItem(internalAdapter.itemCount - 4, false)
                        }
                    }
                    1 -> {
                        scrollAnimator?.cancel()
                        if(!viewPager2.isFakeDragging) {
                            viewPager2.setCurrentItem(internalAdapter.itemCount - 3, false)
                        }
                    }
                    internalAdapter.itemCount - 2 -> {
                        scrollAnimator?.cancel()
                        if(!viewPager2.isFakeDragging) {
                            viewPager2.setCurrentItem(2, false)
                        }
                    }
                    internalAdapter.itemCount - 1 -> {
                        scrollAnimator?.cancel()
                        if(!viewPager2.isFakeDragging) {
                            viewPager2.setCurrentItem(3, false)
                        }
                    }
                    else -> {

                    }
                }

                if(isAutoScroll) {
                    startAutoScroll()
                }
            }
        }

        override fun onPageSelected(position: Int) {
            //원본 아이템이 1개인 경우 -> 처리 안함
            //원본 아이템이 2개인 경우 -> A' B' A B A' B' 총 6개
            //원본 아이템이 3개인 경우 -> B' C' A B C A' B' 총 7개

            val internalAdapter = viewPager2.adapter ?: return
            val fixPosition = if(internalAdapter.itemCount >= 6) {
                when(position) {
                    0 -> {
                        internalAdapter.itemCount - 4
                    }
                    1 -> {
                        internalAdapter.itemCount - 3
                    }
                    internalAdapter.itemCount - 2 -> {
                        2
                    }
                    internalAdapter.itemCount - 1 -> {
                        3
                    }
                    else -> {
                        position
                    }
                } - 2
            } else {
                position
            }

            onPageSelectedListener?.onPageSelectedListener(fixPosition, position)
        }
    }

    /*
        first : 실제 아이템 첫번째 위치
        second : 생성된 아이템 목록
     */
    fun <T> generateLoopItem(items: List<T>): Pair<Int, List<T>> {
        val itemSize = items.size
        val loopItems = ArrayList<T>()
        val prevLoopItems = if(itemSize > 1) { items.takeLast(2) } else { emptyList() }
        val postLoopItems = if(itemSize > 1) { items.take(2) } else { emptyList() }

        loopItems.run {
            addAll(prevLoopItems)
            addAll(items)
            addAll(postLoopItems)
        }

        return prevLoopItems.size to loopItems
    }

    fun startAutoScroll() {
        isAutoScroll = true
        vp2LoopHandler?.removeMessages(0)
        vp2LoopHandler?.sendEmptyMessageDelayed(0, 5000L)
    }

    fun stopAutoScroll() {
        isAutoScroll = false
        vp2LoopHandler?.removeMessages(0)
    }

    private fun getLoopHandler() = LoopHandler(this)

    private class LoopHandler(val helper: InfiniteLoopViewPager2Helper): Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            helper.setCurrentItem(helper.viewPager2.currentItem + 1, 500L)
        }
    }

    fun setCurrentItem(item: Int, duration: Long, interpolator: TimeInterpolator = AccelerateDecelerateInterpolator()) {
        if(viewPager2.isFakeDragging) {
            return
        }

        val pxToDrag: Int = if (viewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            viewPager2.width
        } else {
            viewPager2.height
        } * (item - viewPager2.currentItem)

        scrollAnimator?.cancel()
        scrollAnimator = ValueAnimator.ofInt(0, pxToDrag).apply {
            var previousValue = 0
            addUpdateListener { valueAnimator ->
                val currentValue = valueAnimator.animatedValue as? Int ?: return@addUpdateListener
                val currentPxToDrag = (currentValue - previousValue).toFloat()
                viewPager2.fakeDragBy(-currentPxToDrag)
                previousValue = currentValue
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) { viewPager2.beginFakeDrag() }
                override fun onAnimationEnd(animation: Animator?) { viewPager2.endFakeDrag() }
                override fun onAnimationCancel(animation: Animator?) { viewPager2.endFakeDrag() }
                override fun onAnimationRepeat(animation: Animator?) {}
            })
            this@apply.interpolator = interpolator
            this@apply.duration = duration
            start()
        }
    }

    interface OnPageSelectedListener {
        /*
            position - 인디케이터 표시용 위치
            realPosition - 실제 데이터 리스트 상의 위치
         */
        fun onPageSelectedListener(position: Int, realPosition: Int)
    }
}