package main.java.model;

import main.java.utility.Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vehicle {
    public enum BatteryConsumptionPerMinute {

    }
    public enum BatteryType {
        NI_NH, LI_ION
    }
    private BatteryType fBatteryType;
    private double fBatteryPercentage;
    private Patient.Type fPatientType;
    private double fBatteryConsumption;
    private boolean fCanReachDestination;
    private int fTimeTaken;
    private List<Clsc> fPathTaken;

    public Vehicle(BatteryType batteryType, Patient.Type patientType) {
        fBatteryType = batteryType;
        fBatteryPercentage = 100.00;
        fTimeTaken = 0;
        fPatientType = patientType;
        setBatteryConsumption(patientType, batteryType);
        fPathTaken = new ArrayList<>();
    }

    public Vehicle(Vehicle vehicle) {
        fBatteryType = vehicle.fBatteryType;
        fBatteryPercentage = vehicle.fBatteryPercentage;
        fPatientType = vehicle.fPatientType;
        fBatteryConsumption = vehicle.fBatteryConsumption;
        fTimeTaken = vehicle.fTimeTaken;
        fPathTaken = vehicle.fPathTaken;
    }

    public void setBatteryType(BatteryType batteryType) {
        fBatteryType = batteryType;
        setBatteryConsumption(fPatientType, batteryType);
    }

    public void charge() {
        fTimeTaken += 120;
        fBatteryPercentage = 100.0;
    }

    private void resetVehicle() {
        fTimeTaken = 0;
        fBatteryPercentage = 100.0;
    }

    public Vehicle getShortestPath(Clsc startPoint, Clsc endPoint, Graph graph, Patient.Type patientType) {
        //HashMap<Clsc, Path> shortestPathsFromStart = getAllShortestPaths(startingPoint, graph);
        Vehicle vehicle = new Vehicle(Vehicle.BatteryType.NI_NH, patientType);
        // Try to reach destination with the NI_NH battery
       // if (vehicle.tryToReachDestination(getShortestPathBetweenTwoNodes(startingPoint, finishPoint, shortestPathsFromStart))) {
        if (vehicle.tryToReachDestination(startPoint, endPoint, startPoint, endPoint, graph, new Vehicle(this)) != null) {
            return vehicle;
        }

        vehicle.setBatteryType(Vehicle.BatteryType.LI_ION);
        resetVehicle();
        // If that didn't work, try reaching the destination with the LI_ION battery
        if (vehicle.tryToReachDestination(startPoint, endPoint, startPoint, endPoint, graph, this) != null) {
            return vehicle;
        }

        // If that also didn't work, then there is no way to reach the destination
        return  null;

    }

    public Vehicle tryToReachDestination(Clsc startPoint, Clsc endPoint, Clsc currentNode, Clsc targetNode, Graph graph, Vehicle vehicle) {
        List<Clsc> pathToDestination = Dijkstra.getShortestPathBetweenTwoNodes(currentNode, targetNode, Dijkstra.getAllShortestPaths(startPoint, graph));
        if (reachWithoutStop(pathToDestination, vehicle) != null) {
            return reachWithoutStop(pathToDestination, new Vehicle(vehicle));
        }

        if (currentNode.hasCharge() && currentNode != startPoint) {
            charge();
        }

        List<Clsc> clscWithTerminal = getReachableChargingTerminals(startPoint, graph);
        Vehicle bestVehiclePath = null;
        for (int i = 0; i < clscWithTerminal.size(); ++i) {
            new Vehicle(vehicle).tryToReachDestination(startPoint, endPoint, currentNode, clscWithTerminal.get(i) )
        }
    }

    private Vehicle reachWithoutStop(List<Clsc> path, Vehicle vehicle) {
        for (int i = 0; i < path.size() - 1; ++i) {
            if (tryToReachNode(path.get(i), path.get(i + 1), vehicle)) {
                return null;
            }
        }
        return vehicle;
    }

    private boolean tryToReachNode(Clsc startingNode, Clsc nodeToReach, Vehicle vehicle) {
        if (fBatteryPercentage - calculateBatteryUsage(Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach)) >= 20.0) {
            fBatteryPercentage -= calculateBatteryUsage(Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach));
            fTimeTaken += Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach);
            return true;
        }
        return false;
    }

    public double calculateBatteryUsage(Integer duration) {
        return duration * fBatteryConsumption;
    }

    private void setBatteryConsumption(Patient.Type patientType, BatteryType batteryType) {
        if (batteryType == BatteryType.NI_NH) {
            if (patientType == Patient.Type.LOW_RISK) {
                fBatteryConsumption = 6 / 60.0;
            }
            else if (patientType == Patient.Type.MEDIUM_RISK) {
                fBatteryConsumption = 12 / 60.0;
            }
            else {
                fBatteryConsumption = 48 / 60.0;
            }
        }
        else {
            if (patientType == Patient.Type.LOW_RISK) {
                fBatteryConsumption = 5 / 60.0;
            }
            else if (patientType == Patient.Type.MEDIUM_RISK) {
                fBatteryConsumption = 10 / 60.0;
            }
            else {
                fBatteryConsumption = 30 / 60.0;
            }
        }
    }

    private List<Clsc> getReachableChargingTerminals(Clsc currentPosition, Graph graph, Vehicle vehicle) {
        List<Clsc> reachableTerminals = new ArrayList<>();
        for (int i = 0; i < graph.getClscWithTerminal().size(); ++i) {
            if (graph.getClscWithTerminal().get(1) == currentPosition) {
                break;
            }
            if (tryToReachNode(currentPosition, new Vehicle()))
        }
        return reachableTerminals;
    }

    private boolean wentOverNodeTwice(Clsc targetNode) {
        int counter = 0;
        for (int i = 0; i < fPathTaken.size(); ++i) {
            if (fPathTaken.get(i) == targetNode) {
                ++counter;
            }
        }
        if (counter >= 2) {
            return true;
        }
        return false;
    }





}
