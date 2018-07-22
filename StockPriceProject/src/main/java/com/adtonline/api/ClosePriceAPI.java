package com.adtonline.api;

import java.time.LocalDate;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.adtonline.model.AverageClosePrice200DayModel;
import com.adtonline.model.AverageClosePriceModel;
import com.adtonline.model.ClosePriceModel;
import com.adtonline.model.ErrorModel;
import com.adtonline.model.FailedResponseModel;
import com.adtonline.model.PricesModel;
import com.adtonline.services.ClosePriceService;

@RestController
@RequestMapping(value = "/api/v2")
public class ClosePriceAPI {
  @Autowired
  private ClosePriceService closePriceService;

  @GetMapping(value = "/{tickersymbol}/closeDate", produces = MediaType.APPLICATION_JSON)
  public @ResponseBody ResponseEntity<Object> getClosePriceByDate(
      @PathVariable("tickersymbol") String ticker, @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate) {
    PricesModel pricesModel = new PricesModel();
    pricesModel.setTicker(ticker);
    List<ClosePriceModel> closePrices;
    try {
      closePrices = closePriceService.closePriceByDate("WIKI/" + ticker, startDate, endDate);
      pricesModel.setClosePriceModels(closePrices);
    } catch (Exception e) {
      FailedResponseModel failedResponseModel =
          new FailedResponseModel(LocalDate.now().toString(), e.getMessage());
      return new ResponseEntity<Object>(new ErrorModel(failedResponseModel), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Object>(pricesModel, HttpStatus.OK);
  }

  @GetMapping(value = "/{tickersymbol}/200dma", produces = MediaType.APPLICATION_JSON)
  public @ResponseBody ResponseEntity<Object> getAverage200DayClosePriceByDate(
      @PathVariable("tickersymbol") String ticker, @RequestParam("startDate") String startDate) {
    AverageClosePrice200DayModel averageClosePrice200DayModel = new AverageClosePrice200DayModel();
    try {
      AverageClosePriceModel avergeCloseDate =
          closePriceService.averageClosePrice200Day("WIKI/" + ticker, startDate);
      averageClosePrice200DayModel.setAverageClosePriceModel(avergeCloseDate);
    } catch (Exception e) {
      FailedResponseModel failedResponseModel =
          new FailedResponseModel(LocalDate.now().toString(), e.getMessage());
      return new ResponseEntity<Object>(new ErrorModel(failedResponseModel), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Object>(averageClosePrice200DayModel, HttpStatus.OK);
  }
}
