<template>
    <Row type="flex" :gutter="16">
        <Col>
            <Button @click="parentListeners['edit-info'](user)">编辑信息</Button>
            <br>
            <Button @click="parentListeners['edit-role'](user)">编辑权限</Button>
            <br>
            <Button
                :type="{'normal': 'error', 'blocked': 'success'}[user.state]"
                :disabled="user.state == 'inactive'"
            >{{ {"normal": "禁用", "blocked": "恢复", "inactive": "未激活"}[user.state] }}
            </Button>
        </Col>
        <Col>
            描述： {{ user.description }}
            <br>
            性别： {{ user.gender }}
            <br>
            生日: {{ user.birthday ? user.birthday.toLocaleDateString() : "" }}
            <br>
            姓名： {{ user.name }}
            <br>
            手机： {{ user.phone }}
            <br>
            邮箱： {{ user.email }}
            <br>
            qq： {{ user.qq }}
        </Col>
    </Row>
</template>

<script>
    export default {
        props: {
            user: Object,
            parentListeners: Object
        }
    };
</script>

<style>

</style>
