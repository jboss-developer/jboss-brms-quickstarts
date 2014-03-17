/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author rafaelbenevides
 *
 */
package org.jboss.quickstarts.planner.servlet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.ImageIcon;

import org.jboss.quickstarts.planner.model.Customer;
import org.jboss.quickstarts.planner.model.Depot;
import org.jboss.quickstarts.planner.model.Location;
import org.jboss.quickstarts.planner.model.Vehicle;
import org.jboss.quickstarts.planner.model.VehicleRoutingSolution;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

/**
 * This class is used to draw the {@link Depot}, {@link Customer}, {@link Vehicle} position
 * 
 */
public class VehicleRoutingSchedulePainter {

    private static final int TEXT_SIZE = 12;
    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

    private static final String IMAGE_PATH_PREFIX = "/images/";

    private ImageIcon depotImageIcon;
    private ImageIcon[] vehicleImageIcons;

    private BufferedImage canvas = null;
    private LatitudeLongitudeTranslator translator = null;

    public VehicleRoutingSchedulePainter() {
        depotImageIcon = new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "depot.png"));
        vehicleImageIcons = new ImageIcon[] {
            new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleChameleon.png")),
            new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleButter.png")),
            new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleSkyBlue.png")),
            new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleChocolate.png")),
            new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehiclePlum.png")),
        };
        if (vehicleImageIcons.length != TangoColorFactory.SEQUENCE_1.length) {
            throw new IllegalStateException("The vehicleImageIcons length (" + vehicleImageIcons.length
                + ") should be equal to the TangoColorFactory.SEQUENCE length ("
                + TangoColorFactory.SEQUENCE_1.length + ").");
        }
    }

    public BufferedImage getCanvas() {
        return canvas;
    }

    public void reset(VehicleRoutingSolution schedule, Dimension size, ImageObserver imageObserver) {
        translator = new LatitudeLongitudeTranslator();
        for (Customer costumer : schedule.getCustomerList()) {
            Location location = costumer.getLocation();
            translator.addCoordinates(location.getLatitude(), location.getLongitude());
        }
        for (Vehicle vehicle : schedule.getVehicleList()) {
            Location location = vehicle.getLocation();
            translator.addCoordinates(location.getLatitude(), location.getLongitude());
        }

        double width = size.getWidth();
        double height = size.getHeight();
        translator.prepareFor(width, height - 10 - TEXT_SIZE);

        Graphics2D g = createCanvas(width, height);
        g.setFont(g.getFont().deriveFont((float) TEXT_SIZE));
        g.setStroke(TangoColorFactory.NORMAL_STROKE);
        for (Customer customer : schedule.getCustomerList()) {
            Location location = customer.getLocation();
            int x = translator.translateLongitudeToX(location.getLongitude());
            int y = translator.translateLatitudeToY(location.getLatitude());
            g.setColor(TangoColorFactory.ALUMINIUM_4);
            g.fillRect(x - 1, y - 1, 3, 3);
            String demandString = Integer.toString(customer.getDemand());
            g.drawString(demandString, x - (g.getFontMetrics().stringWidth(demandString) / 2), y - TEXT_SIZE / 2);
        }
        g.setColor(TangoColorFactory.ALUMINIUM_3);
        Depot depot = schedule.getDepot();
        int x = translator.translateLongitudeToX(depot.getLocation().getLongitude());
        int y = translator.translateLatitudeToY(depot.getLocation().getLatitude());
        g.fillRect(x - 2, y - 2, 5, 5);
        g.drawImage(depotImageIcon.getImage(), x - depotImageIcon.getIconWidth() / 2, y - 2 - depotImageIcon.getIconHeight(), imageObserver);
        int colorIndex = 0;
        for (Vehicle vehicle : schedule.getVehicleList()) {
            g.setColor(TangoColorFactory.SEQUENCE_2[colorIndex]);
            Customer vehicleInfoCustomer = null;
            int longestNonDepotDistance = -1;
            int load = 0;
            for (Customer customer : schedule.getCustomerList()) {
                if (customer.getPreviousStandstill() != null && customer.getVehicle() == vehicle) {
                    load += customer.getDemand();
                    Location previousLocation = customer.getPreviousStandstill().getLocation();
                    int previousX = translator.translateLongitudeToX(previousLocation.getLongitude());
                    int previousY = translator.translateLatitudeToY(previousLocation.getLatitude());
                    Location location = customer.getLocation();
                    x = translator.translateLongitudeToX(location.getLongitude());
                    y = translator.translateLatitudeToY(location.getLatitude());
                    g.drawLine(previousX, previousY, x, y);
                    // Determine where to draw the vehicle info
                    int distance = customer.getMilliDistanceToPreviousStandstill();
                    if (customer.getPreviousStandstill() instanceof Customer) {
                        if (longestNonDepotDistance < distance) {
                            longestNonDepotDistance = distance;
                            vehicleInfoCustomer = customer;
                        }
                    } else if (vehicleInfoCustomer == null) {
                        // If there is only 1 customer in this chain, draw it on a line to the Depot anyway
                        vehicleInfoCustomer = customer;
                    }
                    // Line back to the vehicle depot
                    if (customer.getNextCustomer() == null) {
                        Location vehicleLocation = vehicle.getLocation();
                        int vehicleX = translator.translateLongitudeToX(vehicleLocation.getLongitude());
                        int vehicleY = translator.translateLatitudeToY(vehicleLocation.getLatitude());
                        g.setStroke(TangoColorFactory.FAT_DASHED_STROKE);
                        g.drawLine(x, y, vehicleX, vehicleY);
                        g.setStroke(TangoColorFactory.NORMAL_STROKE);
                    }
                }
            }
            // Draw vehicle info
            if (vehicleInfoCustomer != null) {
                if (load > vehicle.getCapacity()) {
                    g.setColor(TangoColorFactory.SCARLET_2);
                }
                Location previousLocation = vehicleInfoCustomer.getPreviousStandstill().getLocation();
                Location location = vehicleInfoCustomer.getLocation();
                double longitude = (previousLocation.getLongitude() + location.getLongitude()) / 2.0;
                x = translator.translateLongitudeToX(longitude);
                double latitude = (previousLocation.getLatitude() + location.getLatitude()) / 2.0;
                y = translator.translateLatitudeToY(latitude);
                boolean ascending = (previousLocation.getLongitude() < location.getLongitude())
                    ^ (previousLocation.getLatitude() < location.getLatitude());

                ImageIcon vehicleImageIcon = vehicleImageIcons[colorIndex];
                int vehicleInfoHeight = vehicleImageIcon.getIconHeight() + 2 + TEXT_SIZE;
                g.drawImage(vehicleImageIcon.getImage(),
                    x + 1, (ascending ? y - vehicleInfoHeight - 1 : y + 1), imageObserver);
                g.drawString(load + " / " + vehicle.getCapacity(),
                    x + 1, (ascending ? y - 1 : y + vehicleInfoHeight + 1));
            }
            colorIndex = (colorIndex + 1) % TangoColorFactory.SEQUENCE_2.length;
        }

        // Legend
        g.setColor(TangoColorFactory.ALUMINIUM_3);
        g.fillRect(5, (int) height - 12 - TEXT_SIZE - (TEXT_SIZE / 2), 5, 5);
        g.drawString("Depot", 15, (int) height - 10 - TEXT_SIZE);
        String vehiclesSizeString = schedule.getVehicleList().size() + " vehicles";
        g.drawString(vehiclesSizeString,
            ((int) width - g.getFontMetrics().stringWidth(vehiclesSizeString)) / 2, (int) height - 10 - TEXT_SIZE);
        g.setColor(TangoColorFactory.ALUMINIUM_4);
        g.fillRect(6, (int) height - 6 - (TEXT_SIZE / 2), 3, 3);
        g.drawString("Customer: demand", 15, (int) height - 5);
        String customersSizeString = schedule.getCustomerList().size() + " customers";
        g.drawString(customersSizeString,
            ((int) width - g.getFontMetrics().stringWidth(customersSizeString)) / 2, (int) height - 5);
        // Show soft score
        g.setColor(TangoColorFactory.SCARLET_2);
        HardSoftScore score = schedule.getScore();
        if (score != null) {
            String totalDistanceString;
            if (!score.isFeasible()) {
                totalDistanceString = "Not feasible";
            } else {
                double fuel = ((double) -score.getSoftScore()) / 1000.0;
                totalDistanceString = NUMBER_FORMAT.format(fuel) + " fuel";
            }
            g.setFont(g.getFont().deriveFont(Font.BOLD, (float) TEXT_SIZE * 2));
            g.drawString(totalDistanceString,
                (int) width - g.getFontMetrics().stringWidth(totalDistanceString) - 10, (int) height - 10);
        }
    }

    public Graphics2D createCanvas(double width, double height) {
        int canvasWidth = (int) Math.ceil(width) + 1;
        int canvasHeight = (int) Math.ceil(height) + 1;
        canvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        return g;
    }
}
