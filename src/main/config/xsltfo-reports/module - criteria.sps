<?xml version="1.0" encoding="UTF-8"?>
<structure version="8" xsltversion="1" cssmode="strict" relativeto="*SPS" encodinghtml="UTF-8" encodingrtf="ISO-8859-1" encodingpdf="UTF-8" useimportschema="1" embed-images="1">
	<parameters/>
	<schemasources>
		<namespaces/>
		<schemasources>
			<xsdschemasource name="XML" main="1" schemafile="jms.xsd" workingxmlfile="jms.xml">
				<xmltablesupport/>
				<textstateicons/>
			</xsdschemasource>
		</schemasources>
	</schemasources>
	<modules/>
	<flags>
		<scripts/>
		<globalparts/>
		<designfragments/>
		<pagelayouts/>
	</flags>
	<scripts>
		<script language="javascript"/>
	</scripts>
	<globalstyles>
		<rules selector=".info">
			<media>
				<media value="all"/>
			</media>
			<rule background-color="#f6f6ff" border="1px solid navy" color="navy" font-weight="bold" margin-bottom="12px" margin-top="12px" padding="2px"/>
		</rules>
		<rules selector=".explanation">
			<media>
				<media value="all"/>
			</media>
			<rule color="blue" font-style="italic" margin-bottom="6px"/>
		</rules>
	</globalstyles>
	<mainparts>
		<children>
			<globaltemplate subtype="main" match="/">
				<children>
					<paragraph paragraphtag="h2">
						<styles border-bottom="2px solid navy" color="navy"/>
						<children>
							<text fixtext="Design Fragments"/>
						</children>
					</paragraph>
					<paragraph paragraphtag="p">
						<properties class="info"/>
						<children>
							<text fixtext="This file is included into another example file. Note that, being a module, the contents of the main template here (including this note) are normally disregarded."/>
						</children>
					</paragraph>
				</children>
			</globaltemplate>
		</children>
	</mainparts>
	<globalparts/>
	<pagelayout>
		<properties paperheight="11.69in" paperwidth="8.27in" size="A4"/>
	</pagelayout>
	<designfragments>
		<children>
			<globaltemplate subtype="named" match="Criteria">
				<children>
					<paragraph paragraphtag="h3">
						<children>
							<text fixtext="Report Criteria"/>
						</children>
					</paragraph>
					<paragraph paragraphtag="p">
						<properties class="explanation"/>
						<children>
							<text fixtext="User selection criteria during report generation listed here:"/>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@dateStart)&gt;0">
												<children>
													<text fixtext="Date Start: "/>
													<template subtype="attribute" match="dateStart">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@dateEnd)&gt;0">
												<children>
													<text fixtext="Date End: "/>
													<template subtype="attribute" match="dateEnd">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@fileId)&gt;0">
												<children>
													<text fixtext="File No: "/>
													<template subtype="attribute" match="fileId">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@workGroup)&gt;0">
												<children>
													<text fixtext="Work Group: "/>
													<template subtype="attribute" match="workGroup">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@supplier)&gt;0">
												<children>
													<text fixtext="Supplier: "/>
													<template subtype="attribute" match="supplier">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@actionCodes)&gt;0">
												<children>
													<text fixtext="Action Code(s): "/>
													<template subtype="attribute" match="actionCodes">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
					<paragraph paragraphtag="h4">
						<children>
							<template subtype="element" match="Criteria">
								<children>
									<condition>
										<children>
											<conditionbranch xpath="string-length(@active)&gt;0">
												<children>
													<text fixtext="Active: "/>
													<template subtype="attribute" match="active">
														<children>
															<content>
																<format datatype="string"/>
															</content>
														</children>
													</template>
												</children>
											</conditionbranch>
										</children>
									</condition>
								</children>
							</template>
						</children>
					</paragraph>
				</children>
			</globaltemplate>
		</children>
	</designfragments>
</structure>
