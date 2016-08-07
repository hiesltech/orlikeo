package umk.zychu.inzynierka.controller;

import org.springframework.stereotype.Controller;

@Controller
public class Websocket2Controller {
/*
	  @Autowired
	  private SimpMessageSendingOperations template;
	  @Autowired
	  private UserService service;
	  private TaskScheduler scheduler = new ConcurrentTaskScheduler();
	  private List<Stock> stockPrices = new ArrayList<Stock>();
	  private Random rand = new Random(System.currentTimeMillis());

	  
	  *//**
	   * Iterates stock list, update the price by randomly choosing a positive
	   * or negative percentage, then broadcast it to all subscribing clients
	   *//*
	  private void updatePriceAndBroadcast() {
	    for(Stock stock : stockPrices) {
	      double chgPct = rand.nextDouble() * 5.0;
	      if(rand.nextInt(2) == 1) chgPct = -chgPct;
	      stock.setPrice(stock.getPrice() + (chgPct / 100.0 * stock.getPrice()));
	      stock.setTime(new Date());
	    }
	    //TOPIC SESSION
	    template.convertAndSend("/topic/price", 98765);
		  System.out.println("prices");
		*//*  template.convertAndSend("/topic/price", 10000);*//*
	  }
	  
	  *//**
	   * Invoked after bean creation is complete, this method will schedule 
	   * updatePriceAndBroacast every 1 second
	   *//*
	  @PostConstruct
	  private void broadcastTimePeriodically() {
	    scheduler.scheduleAtFixedRate(new Runnable() {
	      @Override public void run() {
	        updatePriceAndBroadcast();
	      }
	    }, 15000);
	  }
	  
	  *//**
	   * Handler to add one stock
	   *//*
	  @MessageMapping("/addStock")
	  public void addStock(Stock stock) throws Exception {
	    stockPrices.add(stock);
	    updatePriceAndBroadcast();
	  }

	  *//**
	   * Handler to remove all stocks
	   *//*
	  @MessageMapping("/removeAllStocks")
	  public void removeAllStocks() {
	    stockPrices.clear();
	    updatePriceAndBroadcast();
	  }
	  
	  *//**
	   * Serve the main page
	   *//*
	  @RequestMapping(value = "/stock", method = RequestMethod.GET)
	  public String home() {
	    return "stock";
	  }*/
}
