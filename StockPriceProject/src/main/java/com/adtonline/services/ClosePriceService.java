package com.adtonline.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.threeten.bp.LocalDate;
import com.adtonline.exceptions.EndDateGreaterThanStartDateException;
import com.adtonline.exceptions.NotDataForStartDateException;
import com.adtonline.exceptions.NotEnough200DayException;
import com.adtonline.model.AverageClosePriceModel;
import com.adtonline.model.ClosePriceModel;
import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;
import com.jimmoores.quandl.classic.ClassicQuandlSession;

@Service
public class ClosePriceService {
  private ClassicQuandlSession session = ClassicQuandlSession.create();
  private Function<Row, ClosePriceModel> getClosePriceFromRowData = (Row r) -> {
    return new ClosePriceModel(r.getString("Date"), r.getString("Close"));
  };

  private static final int AVERAGE_CLOSE_PRICE_200_DAY = 200;

  @PostConstruct
  public void init() {
    this.session = ClassicQuandlSession.create();
  }

  public List<ClosePriceModel> closePriceByDate(String ticker, String startDate, String endDate)
      throws Exception {
    if (StringUtils.isEmpty(ticker) | StringUtils.isEmpty(startDate)
        | StringUtils.isEmpty(endDate)) {
      return Collections.emptyList();
    }

    LocalDate startDateConvert = LocalDate.parse(startDate);
    LocalDate endDateConvert = LocalDate.parse(endDate);
    
    if(!endDateConvert.plusDays(1l).isAfter(startDateConvert)) {
      throw new EndDateGreaterThanStartDateException();
    }
    TabularResult tabularResult = this.session.getDataSet(DataSetRequest.Builder
        .of(ticker)
        .withStartDate(startDateConvert)
        .withEndDate(endDateConvert)
        .build());
    return formatClosePriceModel(tabularResult);
  }

  public AverageClosePriceModel averageClosePrice200Day(String ticker, String startDate) 
      throws Exception{
    if (StringUtils.isEmpty(ticker) | StringUtils.isEmpty(startDate)) {
      return null;
    }

    LocalDate startDateConvert = LocalDate.parse(startDate);

    TabularResult tabularResult = this.session.getDataSet(DataSetRequest.Builder
        .of(ticker)
        .withStartDate(startDateConvert)
        .withMaxRows(AVERAGE_CLOSE_PRICE_200_DAY)
        .build());
    
    int count = tabularResult.size();
    // Not Data With Start Date 
    if(count == 0) {
      TabularResult tabularResultSuggest = this.session.getDataSet(DataSetRequest.Builder
          .of(ticker)
          .withMaxRows(AVERAGE_CLOSE_PRICE_200_DAY)
          .build());
      String dateSuggest = tabularResultSuggest.get(AVERAGE_CLOSE_PRICE_200_DAY - 1).getString("Date");
      throw new NotDataForStartDateException(dateSuggest);
    }
    System.out.println(count);
    // Data Not Enough 200 Day
    if(count < AVERAGE_CLOSE_PRICE_200_DAY) {
      int remainDay = AVERAGE_CLOSE_PRICE_200_DAY - count;
      if(remainDay == 1) {
        remainDay += 1;
      }
      TabularResult tabularResultSuggest = this.session.getDataSet(DataSetRequest.Builder
          .of(ticker)
          .withEndDate(startDateConvert)
          .withMaxRows(remainDay)
          .build());
      System.out.println(tabularResultSuggest.size() + tabularResultSuggest.toPrettyPrintedString());
      String dateSuggest = tabularResultSuggest.get(remainDay - 1).getString("Date");
      throw new NotEnough200DayException(dateSuggest);
    }
    
    AverageClosePriceModel tmp = average200DayClosePrice(tabularResult);
    tmp.setTicker(ticker);
    return tmp;
  }

  private AverageClosePriceModel average200DayClosePrice(TabularResult tabularResult) {
    if(tabularResult == null) {
      return null;
    }
    Double sum = 0d;
    for (Row row : tabularResult) {
      sum += row.getDouble("Close");
    }
    Double average = sum / AVERAGE_CLOSE_PRICE_200_DAY;
    return new AverageClosePriceModel("",average.toString());
  }

  public List<ClosePriceModel> formatClosePriceModel(TabularResult tabularResult) {
    if (tabularResult == null) {
      return Collections.emptyList();
    }
    List<ClosePriceModel> closePriceModels = new ArrayList<>();
    tabularResult.forEach(row -> closePriceModels.add(getClosePriceFromRowData.apply(row)));
    return closePriceModels;
  }
}
