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
package org.jboss.quickstarts.planner.servlet;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.quickstarts.planner.model.VehicleRoutingSolution;

/**
 * Adaptation by:
 * 
 * @author rafaelbenevides
 * 
 */
@WebServlet(value = "/vrpImage.png")
public class VrpImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private VehicleRoutingSchedulePainter schedulePainter = new VehicleRoutingSchedulePainter();
    private Dimension size = new Dimension(640, 480);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Avoid image caching
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");
        HttpSession session = req.getSession();
        VehicleRoutingSolution shownSolution = (VehicleRoutingSolution) session.getAttribute("solution");
        if (shownSolution == null) {
            schedulePainter.createCanvas(size.width, size.height);
        } else {
            schedulePainter.reset(shownSolution, size, null);
        }
        BufferedImage image = schedulePainter.getCanvas();
        resp.setContentType("image/png");
        ImageIO.write(image, "png", resp.getOutputStream());
    }
}
