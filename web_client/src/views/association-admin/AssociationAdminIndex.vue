<template>
<Layout>
    <NavHeader/>
    <Layout>
        <Sider>
            <Dropdown transfer style="display:block;"
                      trigger="custom" :visible="selecting"
                      @on-visible-change="showAssociations($event)"
                      @on-clickoutside="showAssociations(false)">
                <Button long type="info" @click="showAssociations(!selecting)">
                    {{ id ? name : '选择社团' }}
                </Button>
                <div slot="list">
                    <CellGroup v-for="association in associations" :key="association.id"
                               @on-click="selectAssociation(association)">
                        <Cell :title="association.name"/>
                    </CellGroup>
                </div>
            </Dropdown>
            <Menu width="auto" :active-name="active">
				<MenuGroup title="社团相关">
					<MenuItem name="AssociationAdminIndex" :to="{name:'AssociationMemList'}">
						<Icon type="md-home"/>
						社团概览
					</MenuItem>
					<MenuItem name="AssociationAdminArticleList" :to="{name:'AssociationAdminArticleList'}">
						<Icon type="md-document" />
						文章管理
					</MenuItem>
					<MenuItem name="AssociationAdminActivityList" :to="{name:'AssociationAdminActivityList'}">
						<Icon type="md-briefcase" />
						活动管理
					</MenuItem>
				</MenuGroup>
                <MenuGroup title="用户相关">
                    <MenuItem :name="''">
                        <Icon type="ios-chatboxes"/>
						消息通知
                    </MenuItem>
                    <MenuItem :name="''">
                        <Icon type="ios-checkbox" />
                        申请列表
                    </MenuItem>
                </MenuGroup>
            </Menu>
        </Sider>
        <Content style="min-height:420px;margin:10px;">
            <router-view/>
        </Content>
    </Layout>
</Layout>
</template>

<script>
import NavHeader from '@/components/nav/Header'
import { mapState, mapMutations, mapActions } from 'vuex'

export default {
    components: {
        NavHeader
    },
    data() { return {
        active: '',
        selecting: false
    };},
    computed: {
        ...mapState('associationAdmin/currentAssociation', ['id', 'name']),
        ...mapState('common/associationSelector', ['associations']),
        ...mapState('common/currentUser', ['admin'])
    },
    methods: {
        showAssociations(visible) {
            if (visible) {
                let that = this;
                // this.listByIds(admin.assoAdmins)
				this.listByIds(['5d666dd649548c000171a631']).then(() => {
                    that.selecting = true;
                }).catch(err => {
                    that.$Notice.error({
                        title: '可选社团加载失败: ' + err.error,
                        desc: err.message
                    })
                });
            } else {
                this.selecting = false;
            }
        },
        selectAssociation(association) {
            this.setAssociation(association);
        },
        ...mapActions('common/associationSelector', ['listByIds','listByFilters']),
        ...mapMutations('associationAdmin/currentAssociation', ['setAssociation'])
    },
    created() {
        this.active = this.$route.name;
    },
    watch: {
        '$route': function(newValue, oldValue) {
            this.active = newValue.name;
        }
    }
}
</script>
