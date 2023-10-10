package com.Train;

import java.sql.SQLException;
import java.util.List;

import com.railways.Train;

public interface TrainDao {
	
	public List<Train> availableTrains() throws SQLException ;

	List<Train> getAllTrains() throws SQLException;

	void addTrain(Train train) throws SQLException;

	void updateTrain(Train train) throws SQLException;

	void deleteTrain(int trainId) throws SQLException;

	public void viewTrains() throws SQLException;

	public Train getTrainById(int trainId) throws SQLException;

}
