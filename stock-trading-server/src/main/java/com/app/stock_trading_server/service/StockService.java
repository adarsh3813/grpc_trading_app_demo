package com.app.stock_trading_server.service;

import com.app.stock_trading_server.model.Stock;
import com.app.stock_trading_server.repository.StockRepository;
import com.tradingApp.grpc.StockRequest;
import com.tradingApp.grpc.StockResponse;
import com.tradingApp.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StockService extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {

        String stockSymbol = request.getStockSymbol();

        Stock stock = stockRepository.findByStockSymbol(stockSymbol);

        StockResponse response = StockResponse.newBuilder()
                .setStockSymbol(stock.getStockSymbol())
                .setPrice(stock.getPrice())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
