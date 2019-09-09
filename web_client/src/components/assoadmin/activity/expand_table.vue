<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">Job: </span>
                <span class="expand-value">{{ row.job }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">Interest: </span>
                <span class="expand-value">{{ row.interest }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">Birthday: </span>
                <span class="expand-value">{{ row.birthday }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="8">
                <span class="expand-key">Favorite book: </span>
                <span class="expand-value">《{{ row.book }}》</span>
            </Col>
            <Col span="8">
                <span class="expand-key">Favorite movie: </span>
                <span class="expand-value">{{ row.movie }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">Favorite music: </span>
                <span class="expand-value">{{ row.music }}</span>
            </Col>
        </Row>
        <Row style="width: 100px">
            <Col>
                <b-button type="submit" variant="primary" @click="handlePass(row.id)">通过</b-button>
                <b-button type="submit" variant="danger" @click="handleRefuse(row.id)">不通过</b-button>
            </CoL>
        </Row>
    </div>
</template>
<script>
import Axios from 'axios';
export default {
    props: {
        row: Object
    },
    methods: {
        handlePass(userId){
            let activityId = (this.$store.getters.get_activityid).toString()
            let participantIds = []
            participantIds.push(userId)
            let data = {
                type: "signupActivity",
                event: "accepted",
                participantIds: participantIds,
                description: "处理接受活动报名申请"
            }

            Axios
                .post("http://202.120.40.8:30727/callback", data)
                .then(response => {
                    console.log('aceept signup successfully!')
                    this.$router.go(0)
                })
                .catch(e => {
                    console.log(e)
                })
        },
        handleRefuse(userId){
            let activityId = (this.$store.getters.get_activityid).toString()
            let participantIds = []
            participantIds.push(userId)
            let data = {
                type: "signupActivity",
                event: "accepted",
                participantIds: participantIds,
                description: "处理接受活动报名申请"
            }

            Axios
                .post("http://202.120.40.8:30727/callback", data)
                .then(response => {
                    console.log('refuse signup successfully!')
                    this.$router.go(0)
                })
                .catch(e => {
                    console.log(e)
                })
        }
    }
}
</script>
<style scoped>
    .expand-row {
        margin-bottom: 16px;
    }
</style>
