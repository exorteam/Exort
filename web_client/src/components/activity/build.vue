<template>
    <div>
        <b-form @submit="onSubmit" @reset="onReset" v-if="show" style="margin-left: 20%; width: 60%">
            <b-form-group
                id="input-group-1"
                label="活动标题:"
                label-for="input-1"
            >
                <b-form-input
                    id="input-1"
                    v-model="form.title"
                    type="text"
                    required
                    style="width: 180px"
                    placeholder="输入名称"
                ></b-form-input>
            </b-form-group>

            <b-form-group
                id="input-group-1"
                label="活动简介:"
                label-for="input-1"
            >
                <b-form-input
                    id="input-1"
                    v-model="form.content"
                    type="text"
                    required
                    style="width: 500px; height: 200px"
                    placeholder="输入简介"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-2" label="报名时间:" label-for="input-2">
                <b-form-input
                    id="input-2"
                    v-model="form.signup_start_time"
                    required
                    style="width: 180px; margin-bottom: 3px"
                    placeholder="报名起始时间"
                ></b-form-input>
                <b-form-input
                    id="input-2"
                    v-model="form.signup_end_time"
                    required
                    style="width: 180px"
                    placeholder="报名截止时间"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-3" label="比赛时间:" label-for="input-2">
                <b-form-input
                    id="input-2"
                    v-model="form.start_time"
                    required
                    style="width: 180px; margin-bottom: 3px"
                    placeholder="比赛起始时间"
                ></b-form-input>
                <b-form-input
                    id="input-2"
                    v-model="form.end_time"
                    required
                    style="width: 180px"
                    placeholder="比赛截止时间"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-4" label="报名是否需要审核:" label-for="input-2">
                <b-form-checkbox v-model="form.checked１" name="check-button" switch>
                    <!-- <b>(Checked: {{ checked }})</b> -->
                </b-form-checkbox>
            </b-form-group>

            <b-form-group id="input-group-5" label="是否仅社团或组织成员可以参加:" label-for="input-2">
                <b-form-checkbox v-model="form.checked２" name="check-button" switch>
                </b-form-checkbox>
            </b-form-group>

            <b-form-group id="input-group-6" label="最大参加人数:" label-for="input-6">
                <b-form-input v-model="form.numberOfPeople" style="width: 180px"/>
            </b-form-group>

            <b-form-group id="input-group-5" label="报名材料模板:" label-for="input-2">
                <Divider/>
                <b-form>
                    <b-form-group label="参加者信息提交表" label-for="input"/>

                    <!-- <b-form-group>

                    </b-form-group> -->
                </b-form>
                <Divider/>
            </b-form-group>

            <b-button type="submit" variant="primary">保存</b-button>
            <b-button type="reset" variant="danger">发布</b-button>
        </b-form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            form: {
                title: '',
                signup_start_time: '',
                signup_end_time: '',
                start_time: '',
                end_time: '',
                checked1: false,
                checked2: false,
                numberOfPeople: 30
            },
            foods: [{ text: 'Select One', value: null }, 'Carrots', 'Beans', 'Tomatoes', 'Corn'],
            show: true,
        }
    },
    methods: {
        onSubmit(evt) {
            evt.preventDefault()
            alert(JSON.stringify(this.form))
        },
        onReset(evt) {
            evt.preventDefault()
            // Reset our form values
            this.form.email = ''
            this.form.name = ''
            this.form.food = null
            this.form.checked = []
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
            this.show = true
            })
        }
    }
  }
</script>

<style>
</style>