# 前端架构设计

## MVVM 架构

```
|-- View --|----- ViewModel ------ |---------- Model-------------|

        rendered                state
        pages                   getter
user | <--------- | Vue      | <--------- | store | <====> Server
     | ---------> | <script> | ---------> |       |
     |  event       | ^         action
     |       jumping| |         mutation
     |              | |<template>
     |  url         v |
     | <--------- | router |
     | ---------> |        |
     |  event
```

## 项目目录结构

项目代码置于 `src/` 中：
- `assets/`: 代码会引用到的静态(图片)文件
- `views/`: 每个页面的 `.vue` 文件, 其中包括 VM 层的 `<script>` 和 V层的 `<template>`
- `components/`: 各页面用到的可复用的组件
- `router/`: 利用 `vue-router` 实现的路由代码, 将浏览器 url 和对应的页面组织起来
- `store/`: 利用 `vuex` 实现的 M 层模型代码
- `http.js`: 返回包装过的 `axios` 实例, 用于请求后端数据
- `main.js` & `App.vue`: 程序入口

## 输出目录结构

通过 `npm run build` 构建项目, 构建结果输出至 `server/public`:
- `static/`
   - `js/`: 打包好的客户端代码
   - `css/`: 样式表
   - `fonts/`: 字体
   - `img/`: 静态图片文件
- `index.html`: 单页应用的唯一页面

## 开发指南

总共分为三大页面： `system-admin`, `association-admin`, `user`

`store/`, `router/`, `views/` 都是按照这个来组织目录的

### 编写 store

目录结构:
- `common/`: 所有页面和组件共用的模型
- `association-admin/`: 社团管理后台专用的模型
- `system-admin`: 系统管理后台专用的模型
- `user`: 一般用户界面专用的模型

选择合适的目录, 新建一个 `your-model.js`, 并在该目录的 `index.js` 中加入

``` js
// ...
import yourModel from './your-model'

export default {
    modules: {
        // ...
        yourModel
    }
}
```

详细教程见 [Vuex 官方文档](https://vuex.vuejs.org/zh/guide/)

在 `your-model.js` 中, 确定你的模型所包含的数据, 并写为 `state`:

``` js
const state = {
    yourData1: 'defaultValue',
    yourData2: null,
    yourData3: 1,
    yourData4: {
        field: 'value'
    }
}
```

编写这些数据的基本操作 `mutation`:
``` js
const mutations = {
    setData1(state, data) {
        state.yourData1 = data;
    },
    setData2(state, {data}) {
        state.yourData2 = data2;
    },
    incData3(state, inc) {
        state.yourData3 += inc;
    },
    setData4(state, {field1, field2}) {
        state.yourData4.field = field2;
    }
}
```

对于需要请求后端数据的操作实现为 `action`:
``` js
import {api} from '@/http'

const actions = {
    fetchData1: ({commit}) => new Promise((resolve, reject) => {
        return api({
            method: 'get',
            url: '/url/to/resource'
        }).then(res => {
            // res.data is { data: ..., error: '', message: '' }
            commit('setData1', res.data.data.data1);
            resolve();
        }).catch(err => reject(err));
    }),
    updateData2: ({commit}, {field1, field2}) => new Promise((resolve, reject) => {
        return api({
            method: 'post',
            url: '/url/to/resource',
            data: {field: field1 + field2}
        }).then(res => {
            commit('setData2', res.data.data);
            resolve();
        }).catch(err => reject(err));
    })
}
```

### 编写 view

在合适的目录下新建 `YourView.vue`

在 `<script>` 中按需要引入
``` js
import { mapActions, mapMutations, mapState } from 'vuex'
```

将 store 中的方法加入 vue 中

``` js
export default {
    // ...
    computed: {
        ...mapState('common/yourModel', ['yourData1', 'yourData2']),
        ...mapState('associationAdmin/yourAnotherModel', ['yourData'])
    },
    methods: {
        ...mapMutations('common/yourModel', ['setData1']),
        ...mapActions('common/yourModel', ['fetchData1', 'updateData2'])
    }
}
```

然后就可以像访问其他成员一样访问这些方法

``` html
<div v-if="yourData2">
    field1: {{ yourData2.field1 }}
    <br/>
    field2: {{ yourData2.field2}}
</div>
<Form>
    <Input v-bind="editing.field1"/>
    <Input v-bind="editing.field2"/>
    <Button @click="updateData2(editing)"></Button>
</Form>
```

### 编写路由

在对应的路由文件中加入你的页面

``` js
import YourView from '@/views/.../YourView'
//...
    {
        path: 'url-path-to-your-page',
        name: 'NameOfYourPage',
        components: YourView
    }
```
