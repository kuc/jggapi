/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;


/**
 * U�ytkownik serwera rozm�w.
 * @version $Revision: 1.7 $
 * @author mnaglik
 */
public interface IUser {
    /**
     * Zwr�� nick u�ytkownika.
     * @return String
     */
    String getName();

    /**
     * Zwr�� numer u�ytkownika.
     * @return int
     */
    int getNumber();

    /**
     * Zmie� nick u�ytkownika
     * @param name nowe nick u�ytkownika
     */
    void setName(String name);

    /**
     * Zmie� numer u�ytkownika.
     * @param number nowy numer u�ytkownika
     */
    void setNumber(int number);

    /**
     * Zwr�� status u�ytkownika.
     * @return boolean status u�ytkownika <code>true</code>- online
     */
    boolean isOnLine();

    /**
     * Zmie� status u�ytkownika.
     * @param onLine nowy status u�ytkownika
     */
    void setOnLine(boolean onLine);
}
