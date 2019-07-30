<template>
    <div>
        <DatePicker v-model="date" type="daterange" placeholder="yyyy-mm-dd"></DatePicker>
        <TimePicker v-model="time" format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
    </div>
</template>

<script>
export default {
    props: {
        timeStamp:Object
    },
    data(){
        return{
            date: [],
            time: []
        }
    },
    methods: {
    },
    mounted() {
        // this.time.push(this.timeStamp[0].getHours()+":"+this.timeStamp[0].getMinutes())
        // this.time.push(this.timeStamp[1].getHours()+":"+this.timeStamp[1].getMinutes())
        // this.timeStamp[0].setHours(0,0,0,0)
        // this.timeStamp[1].setHours(0,0,0,0)
        this.date.push(this.timeStamp[0])
        this.date.push(this.timeStamp[1])
    },
}
</script>