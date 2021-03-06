package com.reach.tong2;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

@SuppressLint("UseSparseArrays")
public class Person {

	private String mName;
	private HashMap<Integer, ArrayList<String>> mPhone = new HashMap<Integer, ArrayList<String>>();
	private HashMap<Integer, ArrayList<String>> mEmail = new HashMap<Integer, ArrayList<String>>();
	private Bitmap mHeadPhoto;
	private HashMap<Integer, ArrayList<String>> mPostAddress = new HashMap<Integer, ArrayList<String>>();
	public boolean mIsFull;

	public String mHasheadphoto = new String("no");

	public Person() {

	}

	public void addData(int family, int type, ArrayList<String> value) {
		switch (family) {
		case DataManager.FamilyType.FAMILY_PHONE:
			mPhone.put(type, value);
			break;
		case DataManager.FamilyType.FAMILY_EMAIL:
			mEmail.put(type, value);
			break;
		case DataManager.FamilyType.FAMILY_ADDRESS:
			mPostAddress.put(type, value);
			break;
		default:
			break;
		}
	}

	public void addAllFamliyData(int family, HashMap<Integer, ArrayList<String>> value) {
		switch (family) {
		case DataManager.FamilyType.FAMILY_PHONE:
			mPhone = value;
			break;
		case DataManager.FamilyType.FAMILY_EMAIL:
			mEmail = value;
			break;
		case DataManager.FamilyType.FAMILY_ADDRESS:
			mPostAddress = value;
			break;
		}
	}

	public void cleanAllData() {
		mPhone.clear();
		mPostAddress.clear();
		mEmail.clear();
	}

	public ArrayList<String> getData(int family, int type) {
		switch (family) {
		case DataManager.FamilyType.FAMILY_PHONE:
			return mPhone.get(type);
		case DataManager.FamilyType.FAMILY_EMAIL:
			return mEmail.get(type);
		case DataManager.FamilyType.FAMILY_ADDRESS:
			return mPostAddress.get(type);
		}
		return null;
	}

	public void addPhone(ArrayList<String> value, int index) {
		mPhone.put(index, value);
	}

	public void addEmail(ArrayList<String> value, int index) {
		mEmail.put(index, value);
	}

	public void addAddress(ArrayList<String> value, int index) {
		mPostAddress.put(index, value);
	}

	public void addName(String name) {
		mName = name;
	}

	public void addHeadPhoto(Bitmap headPhoto) {
		mHeadPhoto = headPhoto;
	}

	public String getName() {
		return mName;
	}

	public ArrayList<String> getPhone(int index) {
		return mPhone.get(index);
	}

	public ArrayList<String> getEmail(int index) {
		return mEmail.get(index);
	}

	public Bitmap getHeadPhoto() {
		return mHeadPhoto;
	}

	public ArrayList<String> getPostAddress(int index) {
		return mPostAddress.get(index);
	}

}
