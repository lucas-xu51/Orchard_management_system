# Orchard Management System

## 1. Crop Monitoring (draft)
- **Main Page:** Finished
- **Crop Detail Page:** Finished
- **Demand Information Page:** Finished
- **History Page:** Finished

## 固定代码
- **顶部栏+back按钮xml代码（如果用linear layout）**

    <LinearLayout
        android:id="@+id/topBar"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:orientation="horizontal"
        android:background="#ADD8E6"
        android:gravity="center_vertical"
        app:layout_constraintTop_toTopOf="parent">
        
        <ImageButton
            android:id="@+id/backButton"
            android:layout_width="35dp"
            android:layout_height="38dp"
            android:layout_marginStart="8dp"
            android:background="?android:attr/selectableItemBackground"
            android:contentDescription="Back"
            android:scaleType="fitCenter"
            android:src="@drawable/icon_back" />
    </LinearLayout>


