package com.ymc.lib_ijk

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import com.ymc.lib_ijk.ijk.utils.IJKVideoType
import com.ymc.lib_ijk.ijk.video.StandardIJKVideoPlayer
import com.ymc.lib_ijk.ijk.video.base.IJKBaseVideoPlayer
import com.ymc.lib_ijk.ijk.video.base.IJKVideoPlayer

/**
 * Created by shuyu on 2016/12/7.
 * 注意
 * 这个播放器的demo配置切换到全屏播放器
 * 这只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里耶设置上视频全屏的需要的自定义配置
 */
open class SampleControlVideo : StandardIJKVideoPlayer {
    private var mMoreScale: TextView? = null
    private var mChangeRotate: TextView? = null
    private var switchSpeed: TextView? = null

    //记住切换数据源类型
    private var mType = 0
    private var mTransformSize = 0

    //数据源
    private var mSourcePosition = 0
    private var ijkSpeed = 1f

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    constructor(context: Context?, fullFlag: Boolean?) : super(
        context,
        fullFlag
    ) {
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    override fun init(context: Context) {
        super.init(context)
        initView()
    }

    private fun initView() {
        mMoreScale = findViewById<View>(R.id.moreScale) as TextView
        mChangeRotate = findViewById<View>(R.id.change_rotate) as TextView
        switchSpeed = findViewById<View>(R.id.switchSpeed) as TextView

        //切换清晰度
        mMoreScale!!.setOnClickListener(OnClickListener {
            if (!mHadPlay) {
                return@OnClickListener
            }
            when (mType) {
                0 -> {
                    mType = 1
                }
                1 -> {
                    mType = 2
                }
                2 -> {
                    mType = 3
                }
                3 -> {
                    mType = 4
                }
                4 -> {
                    mType = 0
                }
            }
            resolveTypeUI()
        })

        //旋转播放角度
        mChangeRotate!!.setOnClickListener(OnClickListener {
            if (!mHadPlay) {
                return@OnClickListener
            }
            if (mTextureView.rotation - mRotate == 270f) {
                mTextureView.rotation = mRotate.toFloat()
                mTextureView.requestLayout()
            } else {
                mTextureView.rotation = mTextureView.rotation + 90
                mTextureView.requestLayout()
            }
        })
        switchSpeed!!.setOnClickListener {
            when {
                ijkSpeed.toDouble() == 1.0 -> {
                    ijkSpeed = 1.5f
                }
                ijkSpeed == 1.5f -> {
                    ijkSpeed = 2.0f
                }
                ijkSpeed == 2f -> {
                    ijkSpeed = 1.0f
                }
            }
            switchSpeed!!.text = " x$ijkSpeed"
            setSpeedPlaying(ijkSpeed, true)
        }
    }

    override fun onSurfaceSizeChanged(
        surface: Surface,
        width: Int,
        height: Int
    ) {
        super.onSurfaceSizeChanged(surface, width, height)
        resolveTransform()
    }

    /**
     * 处理显示逻辑
     */
    override fun onSurfaceAvailable(surface: Surface) {
        super.onSurfaceAvailable(surface)
        resolveRotateUI()
        resolveTransform()
    }

    /**
     * 处理镜像旋转
     * 注意，暂停时
     */
    private fun resolveTransform() {
        when (mTransformSize) {
            1 -> {
                val transform = Matrix()
                transform.setScale(-1f, 1f, mTextureView.width / 2.toFloat(), 0f)
                mTextureView.setTransform(transform)
                //                mChangeTransform.setText("左右镜像");
                mTextureView.invalidate()
            }
            2 -> {
                val transform = Matrix()
                transform.setScale(1f, -1f, 0f, mTextureView.height / 2.toFloat())
                mTextureView.setTransform(transform)
                //                mChangeTransform.setText("上下镜像");
                mTextureView.invalidate()
            }
            0 -> {
                val transform = Matrix()
                transform.setScale(1f, 1f, mTextureView.width / 2.toFloat(), 0f)
                mTextureView.setTransform(transform)
                //                mChangeTransform.setText("旋转镜像");
                mTextureView.invalidate()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.sample_video
    }

    /**
     * 全屏时将对应处理参数逻辑赋给全屏播放器
     *
     * @param context
     * @param actionBar
     * @param statusBar
     * @return
     */
    override fun startWindowFullscreen(
        context: Context,
        actionBar: Boolean,
        statusBar: Boolean
    ): IJKBaseVideoPlayer {
        val sampleVideo =
            super.startWindowFullscreen(context, actionBar, statusBar) as SampleControlVideo
        sampleVideo.mSourcePosition = mSourcePosition
        sampleVideo.mType = mType
        sampleVideo.mTransformSize = mTransformSize
        //sampleVideo.resolveTransform();
        sampleVideo.resolveTypeUI()
        //sampleVideo.resolveRotateUI();
        //这个播放器的demo配置切换到全屏播放器
        //这只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里耶设置上视频全屏的需要的自定义配置
        //比如已旋转角度之类的等等
        //可参考super中的实现
        return sampleVideo
    }

    /**
     * 推出全屏时将对应处理参数逻辑返回给非播放器
     *
     * @param oldF
     * @param vp
     * @param gsyVideoPlayer
     */
    override fun resolveNormalVideoShow(
        oldF: View,
        vp: ViewGroup,
        gsyVideoPlayer: IJKVideoPlayer
    ) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer)
        if (gsyVideoPlayer != null) {
            val sampleVideo = gsyVideoPlayer as SampleControlVideo
            mSourcePosition = sampleVideo.mSourcePosition
            mType = sampleVideo.mType
            mTransformSize = sampleVideo.mTransformSize
            resolveTypeUI()
        }
    }

    /**
     * 旋转逻辑
     */
    private fun resolveRotateUI() {
        if (!mHadPlay) {
            return
        }
        mTextureView.rotation = mRotate.toFloat()
        mTextureView.requestLayout()
    }

    /**
     * 显示比例
     * 注意，IJKVideoType.setShowType是全局静态生效，除非重启APP。
     */
    private fun resolveTypeUI() {
        if (!mHadPlay) {
            return
        }
        when (mType) {
            1 -> {
                mMoreScale!!.text = "16:9"
                IJKVideoType.setShowType(IJKVideoType.SCREEN_TYPE_16_9)
            }
            2 -> {
                mMoreScale!!.text = "4:3"
                IJKVideoType.setShowType(IJKVideoType.SCREEN_TYPE_4_3)
            }
            3 -> {
                mMoreScale!!.text = "全屏"
                IJKVideoType.setShowType(IJKVideoType.SCREEN_TYPE_FULL)
            }
            4 -> {
                mMoreScale!!.text = "拉伸全屏"
                IJKVideoType.setShowType(IJKVideoType.SCREEN_MATCH_FULL)
            }
            0 -> {
                mMoreScale!!.text = "默认比例"
                IJKVideoType.setShowType(IJKVideoType.SCREEN_TYPE_DEFAULT)
            }
        }
        changeTextureViewShowType()
        if (mTextureView != null) mTextureView.requestLayout()
    }
}