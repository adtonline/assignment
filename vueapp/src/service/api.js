export default {

  getTickerSymbol: async(dataset_code) => {
    return $.ajax({
      url:`https://www.quandl.com/api/v3/datasets/WIKI/${dataset_code}.json`,
      method: 'GET',
      headers: {
        "Accept": "*/*",
        "langCode": 'en',
        "Content-Type": "application/json"
      }
    });
  }

}