package com.helium4.abana.model

import MachineLocker

data class Result(
    val totalCount: Int,
    val items: List<MachineLocker>
)