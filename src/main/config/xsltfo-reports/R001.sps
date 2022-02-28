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
	<modules>
		<module spsfile="module - criteria.sps"/>
	</modules>
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
						<styles border-bottom="2px solid navy" color="navy" text-align="center"/>
						<children>
							<text fixtext="R001: PPU Change details"/>
						</children>
					</paragraph>
					<paragraph paragraphtag="p">
						<properties class="info"/>
						<children>
							<text fixtext="List of all files and the PPU Change details."/>
						</children>
					</paragraph>
					<paragraph paragraphtag="p">
						<properties class="explanation"/>
						<children>
							<text fixtext="List of all files and the PPU Change details."/>
						</children>
					</paragraph>
					<template subtype="source" match="XML">
						<children>
							<template subtype="element" match="JMS">
								<children>
									<calltemplate subtype="named" match="Criteria"/>
									<paragraph paragraphtag="p">
										<properties class="explanation"/>
										<children>
											<text fixtext="PPU Change Suppliers:"/>
										</children>
									</paragraph>
									<tgrid>
										<properties border="1"/>
										<children>
											<tgridbody-cols>
												<children>
													<tgridcol/>
													<tgridcol/>
													<tgridcol/>
													<tgridcol/>
												</children>
											</tgridbody-cols>
											<tgridheader-rows>
												<children>
													<tgridrow>
														<children>
															<tgridcell>
																<children>
																	<text fixtext="File No"/>
																</children>
															</tgridcell>
															<tgridcell>
																<children>
																	<text fixtext="PPU Change Supplier"/>
																</children>
															</tgridcell>
															<tgridcell>
																<children>
																	<text fixtext="PPU Change Date"/>
																</children>
															</tgridcell>
															<tgridcell>
																<children>
																	<text fixtext="PPU ChangeTime"/>
																</children>
															</tgridcell>
														</children>
													</tgridrow>
												</children>
											</tgridheader-rows>
											<tgridbody-rows>
												<children>
													<template subtype="element" match="R001">
														<children>
															<tgridrow>
																<children>
																	<tgridcell>
																		<children>
																			<template subtype="attribute" match="fileId">
																				<children>
																					<content>
																						<format datatype="string"/>
																					</content>
																				</children>
																			</template>
																		</children>
																	</tgridcell>
																	<tgridcell>
																		<children>
																			<template subtype="attribute" match="supplierName">
																				<children>
																					<content>
																						<format datatype="string"/>
																					</content>
																				</children>
																			</template>
																		</children>
																	</tgridcell>
																	<tgridcell>
																		<children>
																			<template subtype="attribute" match="changeDate">
																				<children>
																					<content>
																						<format datatype="string"/>
																					</content>
																				</children>
																			</template>
																		</children>
																	</tgridcell>
																	<tgridcell>
																		<children>
																			<template subtype="attribute" match="changeTime">
																				<children>
																					<content>
																						<format datatype="string"/>
																					</content>
																				</children>
																			</template>
																		</children>
																	</tgridcell>
																</children>
															</tgridrow>
														</children>
													</template>
												</children>
											</tgridbody-rows>
										</children>
									</tgrid>
								</children>
							</template>
						</children>
					</template>
				</children>
			</globaltemplate>
		</children>
	</mainparts>
	<globalparts/>
	<pagelayout>
		<properties paperheight="11.69in" paperwidth="8.27in" size="A4"/>
		<children>
			<globaltemplate subtype="pagelayout" match="headerall">
				<children>
					<paragraph>
						<styles text-align="center"/>
						<children>
							<text fixtext="List of all files and the PPU Change details">
								<styles border-bottom="1px solid black" font-weight="bold"/>
							</text>
						</children>
					</paragraph>
				</children>
			</globaltemplate>
			<globaltemplate subtype="pagelayout" match="footerodd">
				<children>
					<tgrid>
						<properties width="100%"/>
						<styles width="100%"/>
						<children>
							<tgridbody-cols>
								<children>
									<tgridcol/>
									<tgridcol>
										<properties width="150"/>
									</tgridcol>
								</children>
							</tgridbody-cols>
							<tgridbody-rows>
								<children>
									<tgridrow>
										<properties height="30"/>
										<children>
											<tgridcell>
												<styles padding="0"/>
											</tgridcell>
											<tgridcell joinleft="1">
												<properties height="30"/>
												<styles padding="0"/>
											</tgridcell>
										</children>
									</tgridrow>
									<tgridrow>
										<children>
											<tgridcell>
												<styles padding="0"/>
												<children>
													<line>
														<properties color="black" size="1"/>
														<styles top="-37pt"/>
													</line>
												</children>
											</tgridcell>
											<tgridcell joinleft="1">
												<styles padding="0"/>
											</tgridcell>
										</children>
									</tgridrow>
									<tgridrow>
										<children>
											<tgridcell>
												<styles font-size="10pt" padding="0" width="50%"/>
												<children>
													<text fixtext="Page: ">
														<styles font-weight="bold"/>
													</text>
													<field>
														<styles font-weight="bold"/>
													</field>
												</children>
											</tgridcell>
											<tgridcell>
												<properties align="right"/>
												<styles font-size="10pt" padding="0" width="50%"/>
												<children>
													<text fixtext="Document: R001">
														<styles font-weight="bold"/>
													</text>
												</children>
											</tgridcell>
										</children>
									</tgridrow>
								</children>
							</tgridbody-rows>
						</children>
					</tgrid>
				</children>
			</globaltemplate>
			<globaltemplate subtype="pagelayout" match="footereven">
				<children>
					<tgrid>
						<properties width="100%"/>
						<styles width="100%"/>
						<children>
							<tgridbody-cols>
								<children>
									<tgridcol/>
									<tgridcol/>
								</children>
							</tgridbody-cols>
							<tgridbody-rows>
								<children>
									<tgridrow>
										<properties height="30"/>
										<children>
											<tgridcell>
												<styles padding="0"/>
											</tgridcell>
											<tgridcell joinleft="1">
												<properties height="30"/>
												<styles padding="0"/>
											</tgridcell>
										</children>
									</tgridrow>
									<tgridrow>
										<children>
											<tgridcell>
												<styles padding="0"/>
												<children>
													<line>
														<properties color="black" size="1"/>
														<styles top="-37pt"/>
													</line>
												</children>
											</tgridcell>
											<tgridcell joinleft="1">
												<styles padding="0"/>
											</tgridcell>
										</children>
									</tgridrow>
									<tgridrow>
										<children>
											<tgridcell>
												<properties align="left"/>
												<styles font-size="10pt" padding="0" width="50%"/>
												<children>
													<text fixtext="Document: R001">
														<styles font-weight="bold"/>
													</text>
												</children>
											</tgridcell>
											<tgridcell>
												<properties align="right"/>
												<styles font-size="10pt" padding="0" width="50%"/>
												<children>
													<text fixtext="Page: ">
														<styles font-weight="bold"/>
													</text>
													<field>
														<styles font-weight="bold"/>
													</field>
												</children>
											</tgridcell>
										</children>
									</tgridrow>
								</children>
							</tgridbody-rows>
						</children>
					</tgrid>
				</children>
			</globaltemplate>
			<globaltemplate subtype="pagelayout" match="coverpage">
				<children>
					<tgrid>
						<properties align="center" width="80%"/>
						<children>
							<tgridbody-cols>
								<children>
									<tgridcol>
										<properties width="20"/>
									</tgridcol>
									<tgridcol/>
								</children>
							</tgridbody-cols>
							<tgridbody-rows>
								<children>
									<tgridrow>
										<properties height="50"/>
										<children>
											<tgridcell/>
											<tgridcell/>
										</children>
									</tgridrow>
									<tgridrow>
										<children>
											<tgridcell joinabove="1">
												<styles padding="2pt"/>
											</tgridcell>
											<tgridcell>
												<styles padding="2pt"/>
												<children>
													<paragraph paragraphtag="fieldset">
														<children>
															<paragraph paragraphtag="center">
																<children>
																	<paragraph paragraphtag="h1">
																		<children>
																			<newline/>
																			<text fixtext="R001: PPU Change details"/>
																			<newline/>
																		</children>
																	</paragraph>
																	<paragraph paragraphtag="h2">
																		<children>
																			<text fixtext="Report"/>
																		</children>
																	</paragraph>
																	<newline/>
																	<newline/>
																	<newline/>
																	<newline/>
																	<newline/>
																	<paragraph paragraphtag="h3">
																		<children>
																			<text fixtext="List of all files and the PPU Change details"/>
																		</children>
																	</paragraph>
																</children>
															</paragraph>
														</children>
													</paragraph>
												</children>
											</tgridcell>
										</children>
									</tgridrow>
								</children>
							</tgridbody-rows>
						</children>
					</tgrid>
				</children>
			</globaltemplate>
		</children>
	</pagelayout>
	<designfragments/>
</structure>
