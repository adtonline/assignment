const normalizerDataChart = (raw) => {
  return {
    type: 'line',
    data: {
      labels: [],
      datasets: {
        data: raw,
        backgroundColor: [
          'rgba(71, 183,132,.5)', // Green
        ],
        borderColor: [
          '#47b784',
        ],
        borderWidth: 3
      }
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
}
export default{
  normalizerDataChart
}