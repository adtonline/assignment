<template>
  <div class="demo">
    <label> Symbol:</label>
    <input v-model="symbol" @keyup="checkSymbol()" class="input-symbol"/>
    <button type="button" @click="callApi()" class="btn-search-symbol">Search</button>
    <p v-if="errors.length" class="error-form d-flex justify-content-center">
      <b>Please correct the following error(s):</b>
      <ul>
        <li v-for="error in errors">{{ error }}</li>
      </ul>
  </p>
  <div class="d-flex justify-content-around">
    <button class="btn-date" @click="chartDate(7)">1 week</button>
    <button class="btn-date" @click="chartDate(30)">1 month</button>
    <button class="btn-date" @click="chartDate(90)">3 months</button>
    <button class="btn-date" @click="chartDate(180)">6 months</button>
    <button class="btn-date" @click="chartDate(356)">1 year</button>
    <button class="btn-date" @click="chartDate(1780)">5 years</button>
    <!-- <button class="btn-date" @click="chartAll()">All</button> -->
  </div>
  <div>
    <canvas id="chart-price"></canvas>
  </div>
  <div>
    <canvas id="chart-demo"></canvas>
  </div>
  </div>
</template>
<script>
import api from "../service/api.js";
import Chart from "chart.js";
import normalizerDataChart from "../utils/normalizer-data.js";
import planetChartData from "../data/chart-data.js";
export default {
  name: 'demo',
  data() {
    return {
      dataTickerSymbol: {},
      listIndex: [],
      listClosePrice: [],
      listAll: [],
      listDate: [],
      symbol: '',
      errors: [],
      planetChartData: planetChartData
    };
  },
  mounted() { 
    this.createChart('chart-demo', this.planetChartData);
  },
  methods: {
    chartDate(index) {
      this.listIndex = [];
      this.listClosePrice = [];
      this.listDate = [];
      for( let i = 0; i < index; i++) {
        this.listIndex.push(this.dataTickerSymbol.dataset.data[i]);
      }
      this.listClosePrice = this.listIndex.map((item, index) => {
        return item[4];
      })
      this.listDate = this.listIndex.map((item, index) => {
        return item[0];
      })
      this.drawLineChart();
    },
    checkSymbol() {
      this.errors = [];
    },
    chartAll() {
      this.listAll = [];
      this.listClosePrice = [];
      this.listDate = [];
      this.listAll.push(this.dataTickerSymbol.dataset.data);
      this.listClosePrice = this.listAll.map((item, index) => {
        return item[4];
      })
      this.listDate = this.listAll.map((item, index) => {
        return item[0];
      })
      this.drawLineChart();
    },
    createChart(chartId, chartData) {
      const ctx = document.getElementById('chart-price');
      const myChart = new Chart (ctx, {
        type: chartData.type,
        data: chartData.data,
        options: chartData.options,
      });
    },
    async callApi() {
      this.errors = [];
      this.listIndex = [];
      this.listClosePrice = [];
      this.listDate = [];
      try {
        let symbol = this.symbol.toUpperCase();
        this.dataTickerSymbol = await $.ajax({
          url:`https://www.quandl.com/api/v3/datasets/WIKI/${symbol}.json`,
          method: 'GET',
          headers: {
            "Accept": "*/*",
            "langCode": 'en',
            "Content-Type": "application/json"
          }
        });
        this.listAll.push(this.dataTickerSymbol.dataset.data);
        for( let i = 0; i < 291; i++) {
          this.listIndex.push(this.dataTickerSymbol.dataset.data[i]);
        }
        this.listClosePrice = this.listIndex.map((item, index) => {
          return item[4];
        })
        this.listDate = this.listIndex.map((item, index) => {
          return item[0];
        })
        this.drawLineChart();
        } catch (error) {
          let test = JSON.parse(error.responseText);
          console.log("errors:", test);
          console.log("errors quandl_error:", test.quandl_error);
          if(test.quandl_error.code) {
            this.errors.push(test.quandl_error.message);
          }
        }
      return this.dataTickerSymbol;  
    },
    drawLineChart() { 
     // let chart = normalizerDataChart(this.listClosePrice);
     let dataChart = {
        type: 'line',
        data: {
          labels: this.listDate,
          datasets: [
            {
              label: 'Close Price',
              data: this.listClosePrice,
              backgroundColor: [
                'rgba(71, 183,132,.5)', // Green
              ],
              borderColor: [
                '#47b784',
              ],
              borderWidth: 3
            }
          ]
        },
        options: {
          responsive: true,
          lineTension: 1,
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true,
                padding: 25,
              }
            }]
          }
        }
      }
      console.log("chart:", dataChart);
      this.createChart('chart-price', dataChart); 
    }
  }
};
</script>
<style lang="scss" scoped>
  .demo {
    $color: #fff;
    .error-form {
      background-color: #ea9a9a;
      border: 1px solid red;
      border-radius: 5px;
      color: $color;
    }
    .btn-date {
      background-color: #47b784;
      border: 1px solid #47b784;
      border-radius: 3px;
      color: $color;
      text-align: center;
      padding: 10px;
      width: 100px;
    }
    .input-symbol {
      margin: 10px;
    }
    .btn-search-symbol {
      background-color: #47b784;
      border: 1px solid #47b784;
      border-radius: 3px;
      color: $color;
      text-align: center;
    }
  }

</style>


