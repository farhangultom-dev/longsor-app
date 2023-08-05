package com.example.longsor_app_001.model;

public class DataRiwyatLongsorModelItem{
	private int geoJsonId;
	private String tahun;

	public DataRiwyatLongsorModelItem(int geoJsonId, String tahun) {
		this.geoJsonId = geoJsonId;
		this.tahun = tahun;
	}

	public int getGeoJsonId() {
		return geoJsonId;
	}

	public String getTahun() {
		return tahun;
	}
}
