<?xml version="1.0" encoding="UTF-8"?>

<!-- Generates a SQL DDL from the XML output of the Orthogonal Toolbox			-->
<!-- Orthogonal Toolbox is a free utility produced by Orthogonal Software Corporation	-->
<!-- http://www.orthogonalsoftware.com												-->

<?altova_samplexml OrthogonalToolBoxExport.xml?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
	<xsl:param name="CR" select="'&#0013;&#0010;'"/>
	<xsl:param name="GO" select="';'"/>
	<xsl:param name="SEQUENCE_START_WITH" select="'10001'"/>
	
	<xsl:template match="/VisioModels">
		<xsl:apply-templates select="LogicalModels" />
	</xsl:template>
	
	<xsl:template match="LogicalModels">
		<xsl:for-each select="LogicalModel/Entities/Entity[@IsView='false']">
			<xsl:sort select="@PhysicalName" order="ascending" />
			<xsl:apply-templates select="current()" mode="table"/>
		</xsl:for-each>
		<xsl:for-each select="LogicalModel/Relationships/Relationship">
			<xsl:sort select="FirstAttributes/FirstAttribute/@FirstAttributeID" order="ascending" />
			<xsl:apply-templates select="current()" mode="fk"/>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template match="Entity" mode="table">
		<xsl:variable name="table" select="@PhysicalName"/>
		<xsl:value-of select="concat('CREATE TABLE ', $table, ' (', $CR)"/>
		<xsl:for-each select="EntityAttributes/EntityAttribute">
			<xsl:text>    </xsl:text>
			<xsl:apply-templates select="../../../../Attributes/Attribute[@AttributeID = current()/@EntityAttributeID]" mode="table"/>
			<xsl:choose>
				<xsl:when test="count(../../EntityAttributes/EntityAttribute) != position()">,</xsl:when>
				<xsl:otherwise><xsl:value-of select="concat($CR, ')', $GO)"/></xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="$CR"/>
		</xsl:for-each>
		<xsl:apply-templates select="EntityAnnotations/EntityAnnotation[@AnnotationType='Primary Key']" mode="pk"/>
		<xsl:apply-templates select="EntityAnnotations/EntityAnnotation[@AnnotationType='Alternate Key']" mode="ak"/>
		<xsl:apply-templates select="EntityAnnotations/EntityAnnotation[@AnnotationType='Index']" mode="idx"/>
	</xsl:template>
	
	<!-- Create column for each EntityAttribute -->
	<xsl:template match="Attribute" mode="table">
	<xsl:variable name="nullability">
		<xsl:choose>
			<xsl:when test='@AllowNulls = "false"'>NOT NULL</xsl:when>
			<xsl:otherwise>NULL</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="data-type">
		<xsl:choose>
			<xsl:when test="@PhysicalDatatype = 'INTEGER'">
				<xsl:text>NUMBER(8,0)</xsl:text>
			</xsl:when>
			<xsl:when test="@PhysicalDatatype = 'NUMBERPS(8,0)'">
				<xsl:text>NUMBER(8,0)</xsl:text>
			</xsl:when>
			<xsl:when test="@PhysicalDatatype = 'SHORT'">
				<xsl:text>NUMBER(8,0)</xsl:text>
			</xsl:when>
			<xsl:when test="@PhysicalDatatype = 'LONG'">
				<xsl:text>NUMBER(8,0)</xsl:text>
			</xsl:when>
			<xsl:when test="@PhysicalDatatype = 'CHAR(254)'">
				<xsl:text>VARCHAR2(254)</xsl:text>
			</xsl:when>
			<xsl:when test="@PhysicalDatatype = 'DATETIME'">
				<xsl:text>DATE</xsl:text>
			</xsl:when>
			<xsl:when test="@PhysicalDatatype = 'BINARY(10)'">
				<xsl:text>BLOB</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="@PhysicalDatatype"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:value-of select="concat(@PhysicalName, ' ', $data-type, ' ', $nullability)"/>
</xsl:template>

<xsl:template match="EntityAnnotation" mode="pk">
		<xsl:variable name="table" select="../../@PhysicalName"/>
		<xsl:variable name="pk" select="concat($table, '_PK')"/>
ALTER TABLE <xsl:value-of select="$table"/> ADD CONSTRAINT <xsl:value-of select="$pk"/> PRIMARY KEY (<xsl:for-each select="EntityAnnotationAttributes/EntityAnnotationAttribute"><xsl:value-of select="../../../../../../Attributes/Attribute[@AttributeID = current()/@EntityAnnotationAttributeID]/@PhysicalName"/><xsl:if test="count(../../EntityAnnotationAttributes/EntityAnnotationAttribute) != position()">, </xsl:if></xsl:for-each>)<xsl:value-of select="$GO"/>
	<xsl:value-of select="$CR"/>
	<xsl:value-of select="$CR"/>

<!--xsl:if test="count(EntityAnnotationAttributes/EntityAnnotationAttribute) = 1">
	<xsl:variable name="seq" select="concat($table, '_SEQ')"/>
CREATE SEQUENCE <xsl:value-of select="$seq"/>
    START WITH <xsl:value-of select="$SEQUENCE_START_WITH"/>
    INCREMENT BY 1
    NOMINVALUE
    NOMAXVALUE
    CACHE 20
    NOORDER<xsl:value-of select="$GO"/>
	<xsl:value-of select="$CR"/>
	<xsl:value-of select="$CR"/>
</xsl:if-->

</xsl:template>

<xsl:template match="EntityAnnotation" mode="ak">
	<xsl:variable name="table" select="../../@PhysicalName"/>
CREATE UNIQUE INDEX <xsl:value-of select="@ConstraintName"/> ON <xsl:value-of select="$table"/> (<xsl:for-each select="EntityAnnotationAttributes/EntityAnnotationAttribute"><xsl:value-of select="../../../../../../Attributes/Attribute[@AttributeID = current()/@EntityAnnotationAttributeID]/@PhysicalName"/><xsl:if test="count(../../EntityAnnotationAttributes/EntityAnnotationAttribute) != position()">, </xsl:if></xsl:for-each>)<xsl:value-of select="$GO"/>
	<xsl:value-of select="$CR"/>

</xsl:template>

<xsl:template match="EntityAnnotation" mode="idx">
	<xsl:variable name="table" select="../../@PhysicalName"/>
CREATE INDEX <xsl:value-of select="@ConstraintName"/> ON <xsl:value-of select="$table"/> (<xsl:for-each select="EntityAnnotationAttributes/EntityAnnotationAttribute"><xsl:value-of select="../../../../../../Attributes/Attribute[@AttributeID = current()/@EntityAnnotationAttributeID]/@PhysicalName"/><xsl:if test="count(../../EntityAnnotationAttributes/EntityAnnotationAttribute) != position()">, </xsl:if></xsl:for-each>)<xsl:value-of select="$GO"/>
	<xsl:value-of select="$CR"/>

</xsl:template>

<xsl:template match="Relationship" mode="fk">
	<xsl:variable name="ri">
		<xsl:choose>
			<xsl:when test='@UpdateRule="No Action"'>on update no action </xsl:when>
			<xsl:when test='@UpdateRule="Cascade"'>on update cascade </xsl:when>
			<xsl:when test='@UpdateRule="Set Null"'></xsl:when>
			<xsl:when test='@UpdateRule="Set Default"'></xsl:when>
			<xsl:when test='@UpdateRule="Do Not Enforce"'></xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
		<xsl:choose>
			<xsl:when test='@DeleteRule="No Action"'>on delete no action</xsl:when>
			<xsl:when test='@DeleteRule="Cascade"'>on delete cascade</xsl:when>
			<xsl:when test='@DeleteRule="Set Null"'></xsl:when>
			<xsl:when test='@DeleteRule="Set Default"'></xsl:when>
			<xsl:when test='@DeleteRule="Do Not Enforce"'></xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:variable name="table" select="../../Entities/Entity[@EntityID = current()/@FirstEntityID]/@PhysicalName"/>
	<xsl:variable name="table2" select="../../Entities/Entity[@EntityID = current()/@SecondEntityID]/@PhysicalName"/>
	<xsl:variable name="disable_ri">
		<xsl:choose>
			<xsl:when test='@UpdateRule="Set Null" or @UpdateRule="Set Default" or @UpdateRule="Do Not Enforce" or @DeleteRule="Set Null" or @DeleteRule="Set Default" or @DeleteRule="Do Not Enforce"'><xsl:value-of select="$GO"/>

ALTER TABLE <xsl:value-of select="$table"/> NOCHECK CONSTRAINT <xsl:value-of select="@PhysicalName"/></xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="fk" select="@PhysicalName"/>
ALTER TABLE <xsl:value-of select="$table"/> ADD CONSTRAINT <xsl:value-of select="$fk"/> FOREIGN KEY (<xsl:for-each select="FirstAttributes/FirstAttribute"><xsl:value-of select="../../../../Attributes/Attribute[@AttributeID = current()/@FirstAttributeID]/@PhysicalName"/><xsl:if test="count(../../FirstAttributes/FirstAttribute) != position()">, </xsl:if></xsl:for-each>)
	REFERENCES <xsl:value-of select="$table2"/> (<xsl:for-each select="SecondAttributes/SecondAttribute"><xsl:value-of select="../../../../Attributes/Attribute[@AttributeID = current()/@SecondAttributeID]/@PhysicalName"/><xsl:if test="count(../../SecondAttributes/SecondAttribute) != position()">, </xsl:if></xsl:for-each>
	<!--xsl:value-of select="$ri"/><xsl:value-of select="$disable_ri"/-->
	<xsl:value-of select="concat(')', $GO)"/>
	<xsl:value-of select="$CR"/>
</xsl:template>
	
</xsl:stylesheet>
