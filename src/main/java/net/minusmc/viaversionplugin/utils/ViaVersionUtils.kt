package net.minusmc.viaversionplugin.utils

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion
import de.florianmichael.vialoadingbase.ViaLoadingBase

object ViaVersionUtils {

	@JvmStatic
	fun isCurrentVersionNewerThanOrEqualTo(version: ProtocolVersion): Boolean {
		return ViaLoadingBase.getInstance().targetVersion.newerThanOrEqualTo(version)
	}

	@JvmStatic
	fun isCurrentVersionOlderThanOrEqualTo(version: ProtocolVersion): Boolean {
		return ViaLoadingBase.getInstance().targetVersion.olderThanOrEqualTo(version)
	}

	@JvmStatic
	fun isCurrentVersionInRangeFromTo(fromVersion: ProtocolVersion, toVersion: ProtocolVersion): Boolean {
		return isCurrentVersionNewerThanOrEqualTo(fromVersion) && 
			isCurrentVersionOlderThanOrEqualTo(toVersion)
	}

}