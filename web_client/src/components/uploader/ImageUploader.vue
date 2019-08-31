<template>
<div :style="{display: inline ? 'inline-flex' : 'flex'}">
    <div v-if="value" class="image-uploader-upload-preview">
        <div class="image-uploader-upload-preview-img">
            <img :src="'/files/' + value"/>
        </div>
        <div class="image-uploader-upload-preview-cover">
            <Icon type="ios-trash-outline" @click.native="$emit('input', null)"/>
        </div>
    </div>
    <Upload action="/upload/image" accept="image/*" :max-size="maxSize"
            :show-upload-list="false" with-credentials class="image-uploader-upload"
            :on-success="handleSuccess" :on-exceeded-size="handleMaxSize"
            :on-error="handleError">
        <Button icon="ios-cloud-upload-outline" class="image-uploader-upload-button">
        </Button>
    </Upload>
</div>
</template>

<script>
export default {
    props: {
        value: {
            type: String,
            default: null
        },
        inline: {
            type: Boolean,
            default: false
        },
        maxSize: {
            type: Number,
            default: 512
        }
    },
    methods: {
        handleSuccess(data) {
            this.$emit('input', data.filename);
        },
        handleMaxSize(file) {
            this.$Notice.warning({
                title: '文件大小超过限制',
                desc: '文件 ' + file.name + ' 过大，应不超过 512KB'
            });
        },
        handleError(err) {
            this.$Notice.error({
                title: '上传失败',
                desc: err
            })
        }
    }
}
</script>

<style>
.image-uploader-upload, .image-uploader-upload-preview {
    flex: 0 0 60px;
    height: 60px;
    width: 60px;
}
.image-uploader-upload-preview {
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
}
.image-uploader-upload-button {
    height: 60px;
    width: 60px;
    font-size: 30px;
}
.image-uploader-upload-preview-cover i {
    color: red;
    font-size: 30px;
    cursor: pointer;
}
.image-uploader-upload-preview-cover {
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
    text-align: center;
    line-height: 60px;
}
.image-uploader-upload-preview:hover .image-uploader-upload-preview-cover{
    display: block;
}
.image-uploader-upload-preview-img {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
}
.image-uploader-upload-preview-img img {
    object-fit: contain;
    width: 100%;
    height: 100%;
}
</style>
