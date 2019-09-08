<template>
<div class="md-editor">
    <mavon-editor ref="md" :toolbars="mdOptions" :subfield="false"
                  :toolbars-flag="!readMode" :editable="!readMode"
                  :default-open="readMode?'preview':'edit'"
                  v-model="content" @imgAdd="imgAdd"
                  style="width:100%;height:100%"/>

    <transition name="fade">
        <div class="notice-wrapper"  v-if="error">
            <div class="notice">
                <i @click.stop.prevent="error=null" class="fa fa-mavon-times"
                    aria-hidden="true"></i>
                <h3 class="title">{{ error.error}}</h3>
                <p class="desc">{{ error.message }}</p>
                <div class="op-btn" @click.stop="error=null">确定</div>
            </div>
        </div>
    </transition>
</div>
</template>

<script>
import {fe} from '@/http'

export default {
    props: {
        value: {
            type: String,
            default: ''
        },
        readMode: {
            type: Boolean,
            default: false
        }
    },
    data() { return {
        mdOptions: {
            bold: true, // 粗体
            italic: true, // 斜体
            header: true, // 标题
            underline: true, // 下划线
            strikethrough: true, // 中划线
            mark: true, // 标记
            superscript: true, // 上角标
            subscript: true, // 下角标
            quote: true, // 引用
            ol: true, // 有序列表
            ul: true, // 无序列表
            link: true, // 链接
            imagelink: true, // 图片链接
            code: true, // code
            table: true, // 表格
            help: true, // 帮助
            /* 1.3.5 */
            undo: true, // 上一步
            redo: true, // 下一步
            /* 1.4.2 */
            navigation: true, // 导航目录
            /* 2.2.1 */
            preview: true, // 预览
        },
        content: '',
        processing: false,
        error: null
    };},
    methods: {
        setError(err) {
            this.error = err;
            return err;
        },
        processingReject() {
            return Promise.reject(this.setError({
                error: '任务进行中',
                message: '正在上传或下载，请等待完成后重试'
            }));
        },
        $reload() {
            if (this.processing) {
                return this.processingReject();
            }
            if (this.value) {
                this.processing = true;
                return new Promise((resolve, reject) => {
                    fe.get('/files/' + this.value).then(res => {
                        this.content = res.data;
                        resolve(res.data);
                    }).catch(err => {
                        reject(this.setError({
                            error: '内容下载失败',
                            message: err.message
                        }));
                    }).finally(() => {
                        this.processing = false;
                    });
                });
            }
            this.content = '';
            return Promise.resolve('');
        },
        $save() {
            if (this.processing) {
                return this.processingReject();
            }
            if (this.content) {
                this.processing = true;
                return new Promise((resolve, reject) => {
                    let formData = new FormData();
                    formData.append('file', new File([this.content], 'content.txt', {
                        type: 'text/plain'
                    }));
                    fe({
                        url: '/upload/text',
                        method: 'post',
                        data: formData,
                        headers: { 'Content-Type': 'multipart/form-data' }
                    }).then(res => {
                        this.$emit('input', res.data.filename);
                        resolve(res.data.filename);
                    }).catch(err => {
                        reject(this.setError({
                            error: '内容上传失败',
                            message: err.message
                        }));
                    }).finally(() => {
                        this.processing = false;
                    });
                });
            }
            this.$emit('input', '');
            return Promise.resolve('');
        },
        imgAdd(pos, file) {
            this.processing = true;
            let formData = new FormData();
            formData.append('file', file);
            fe({
                url: '/upload/image',
                method: 'post',
                data: formData,
                headers: { 'Content-Type': 'multipart/form-data' }
            }).then(res => {
                this.$refs.md.$img2Url(pos, '/files/' + res.data.filename);
            }).catch(err => {
                this.$refs.md.$refs.toolbar_left.$imgDel(pos);
                this.setError({
                    error: '图片上传失败',
                    message: err.message
                })
            }).finally(() => {
                this.processing = false;
            });
        }
    },
    mounted() {
        this.$reload();
    }
}
</script>

<style>
.notice-wrapper {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.7);
    z-index: 1600;
    transition: all 0.1s linear 0s;
}
.notice-wrapper.fade-enter-active,.notice-wrapper.fade-leave-active {
    opacity: 1;
}
.notice-wrapper.fade-enter,.notice-wrapper.fade-leave-active {
    opacity: 0;
}
.notice-wrapper .notice {
    position:fixed;
    box-sizing: border-box;
    text-align: center;
    width: 24%;
    left: 38%;
    height: auto;
    padding: 40px;
    top: 25%;
    transition: all 0.1s linear 0s;
    z-index: 3;
    background: #fff;
    border-radius: 2px;
    box-shadow: 0 0px 5px rgba(255,255,255, .156863), 0 0px 5px rgba(255,255,255, .227451);
}

@media only screen and (max-width: 1500px) {
    .notice-wrapper .notice {
        width: 34%;
        left: 33%;
    }
}
@media only screen and (max-width: 1000px) {
    .notice-wrapper .notice {
        width: 50%;
        left: 25%;
    }
}
@media only screen and (max-width: 600px) {
    .notice-wrapper .notice {
        width: 80%;
        left: 10%;
    }
}
.notice-wrapper .notice i {
    font-size: 24px;
    position: absolute;
    right: 8px;
    top: 6px;
    color: rgba(0, 0, 0, 0.7);
    cursor: pointer;
}
.notice-wrapper .notice .title {
    font-size: 20px;
    margin-bottom: 30px;
    margin-top: 10px;
    font-weight: 500 !important;
}
.notice-wrapper .notice .title {
    font-size: 20px;
    margin-bottom: 30px;
    margin-top: 10px;
    font-weight: 500 !important;
}
.notice-wrapper .notice .desc {
    height: 32px;
    line-height: 32px;
    font-size: 15px;
}
.notice-wrapper .notice .op-btn {
    width: 100px;
    height: 35px;
    display: inline-block;
    margin-top: 30px;
    cursor: pointer;
    text-align: center;
    line-height: 35px;
    opacity: 0.9;
    border-radius: 2px;
    letter-spacing: 1px;
    font-size: 15px;
    background: #2185d0;
    color: #fff;
    margin-left: 5%;
}
.notice-wrapper .notice .op-btn:hover {
    opacity: 1;
}
</style>
