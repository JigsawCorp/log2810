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

    public Vehicle(Patient.Type patientType) {
        fBatteryType = BatteryType.NI_NH;
        fBatteryPercentage = 100.00;
        fTimeTaken = 0;
        fPatientType = patientType;
        setBatteryConsumption(patientType, BatteryType.NI_NH);
        fPathTaken = new ArrayList<>();
        fCanReachDestination = false;
    }

    public Vehicle(Vehicle vehicle) {
        fBatteryType = vehicle.fBatteryType;
        fBatteryPercentage = vehicle.fBatteryPercentage;
        fPatientType = vehicle.fPatientType;
        fBatteryConsumption = vehicle.fBatteryConsumption;
        fTimeTaken = vehicle.fTimeTaken;
        fPathTaken = new ArrayList<>(vehicle.fPathTaken);
        fCanReachDestination = vehicle.fCanReachDestination;
    }

    private void copy(Vehicle vehicle) {
        fBatteryType = vehicle.fBatteryType;
        fBatteryPercentage = vehicle.fBatteryPercentage;
        fPatientType = vehicle.fPatientType;
        fBatteryConsumption = vehicle.fBatteryConsumption;
        fTimeTaken = vehicle.fTimeTaken;
        fPathTaken = new ArrayList<>(vehicle.fPathTaken);
        fCanReachDestination = vehicle.fCanReachDestination;
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

    public void getShortestPath(Clsc startPoint, Clsc endPoint, Graph graph) {
        //HashMap<Clsc, Path> shortestPathsFromStart = getAllShortestPaths(startingPoint, graph);
        // Try to reach destination with the NI_NH battery
       // if (vehicle.tryToReachDestination(getShortestPathBetweenTwoNodes(startingPoint, finishPoint, shortestPathsFromStart))) {
        fPathTaken.add(startPoint);
        if (tryToReachDestination(startPoint, endPoint, startPoint, endPoint, graph)) {
            fCanReachDestination = true;
            return;
        }

        setBatteryType(Vehicle.BatteryType.LI_ION);
        resetVehicle();
        // If that didn't work, try reaching the destination with the LI_ION battery
        if (tryToReachDestination(startPoint, endPoint, startPoint, endPoint, graph)) {
            fCanReachDestination = true;
        }

        // If that also didn't work, then there is no way to reach the destination

    }

    public boolean tryToReachDestination(Clsc startPoint, Clsc endPoint, Clsc currentNode, Clsc targetNode, Graph graph) {
        System.out.println("tryToReachDestination(), currentNode = " + currentNode + " , targetNode = " + targetNode);

        if (currentNode.hasCharge() && currentNode != startPoint) {
            charge();
        }

        List<Clsc> pathToDestination = Dijkstra.getShortestPathBetweenTwoNodes(currentNode, targetNode, Dijkstra.getAllShortestPaths(currentNode, graph));
        Vehicle newVehicle = new Vehicle(this);
        if (newVehicle.reachWithoutStop(pathToDestination)) {
            copy(newVehicle);
            return true;
        }

        newVehicle = new Vehicle(this);
        List<Clsc> clscWithTerminal = getReachableChargingTerminals(startPoint, graph);
        Vehicle bestVehiclePath = null;
        for (int i = 0; i < clscWithTerminal.size(); ++i) {
            Vehicle tempVehicle = new Vehicle(this);
            if (tempVehicle.tryToReachDestination(startPoint, endPoint, currentNode, clscWithTerminal.get(i), graph)) {
                if (tempVehicle.tryToReachDestination(startPoint, endPoint, clscWithTerminal.get(i), endPoint, graph))
                if (bestVehiclePath == null) {
                    bestVehiclePath = tempVehicle;
                }
                else {
                    if (tempVehicle.fTimeTaken < bestVehiclePath.fTimeTaken) {
                        bestVehiclePath = tempVehicle;
                    }
                }
            }
        }

        if (bestVehiclePath != null) {
            copy(bestVehiclePath);
            return true;
        }
        return false;
    }

    private boolean reachWithoutStop(List<Clsc> path) {
        for (int i = 0; i < path.size() - 1; ++i) {
            if (!tryToReachNode(path.get(i), path.get(i + 1))) {
                return false;
            }
        }
        //fPathTaken.add(path.get(path.size() - 1));
        return true;
    }

    private boolean tryToReachNode(Clsc startingNode, Clsc nodeToReach) {
        if (fBatteryPercentage - calculateBatteryUsage(Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach)) >= 20.0) {
            fBatteryPercentage -= calculateBatteryUsage(Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach));
            fTimeTaken += Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach);
            fPathTaken.add(nodeToReach);
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

    private List<Clsc> getReachableChargingTerminals(Clsc currentPosition, Graph graph) {
        List<Clsc> reachableTerminals = new ArrayList<>();
        for (int i = 1; i < graph.getClscWithTerminal().size(); ++i) {
            if (graph.getClscWithTerminal().get(i) == currentPosition) {
                break;
            }
            if (wentOverTerminalAlready(graph.getClscWithTerminal().get(i))) {
                break;
            }
            Vehicle newVehicle = new Vehicle(this);
            if (newVehicle.reachWithoutStop(Dijkstra.getShortestPathBetweenTwoNodes(currentPosition, graph.getClscWithTerminal().get(i), Dijkstra.getAllShortestPaths(currentPosition, graph)))) {
                reachableTerminals.add(graph.getClscWithTerminal().get(i));
            }
        }
        return reachableTerminals;
    }

    private boolean wentOverTerminalAlready(Clsc targetNode) {
        for (int i = 0; i < fPathTaken.size(); ++i) {
            if (fPathTaken.contains(targetNode)) {
                return true;
            }
        }
        return false;
    }





}
