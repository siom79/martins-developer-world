package com.wordpress.mdw;

import org.apache.hadoop.conf.Configuration;
import parquet.column.ColumnDescriptor;
import parquet.hadoop.api.WriteSupport;
import parquet.io.ParquetEncodingException;
import parquet.io.api.Binary;
import parquet.io.api.RecordConsumer;
import parquet.schema.MessageType;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

public class CsvWriteSupport extends WriteSupport<List<String>> {
	private final MessageType messageType;
	private RecordConsumer recordConsumer;

	public CsvWriteSupport(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public WriteSupport.WriteContext init(Configuration configuration) {
		return new WriteSupport.WriteContext(messageType, new HashMap<String, String>());
	}

	@Override
	public void prepareForWrite(RecordConsumer recordConsumer) {
		this.recordConsumer = recordConsumer;
	}

	@Override
	public void write(List<String> record) {
		recordConsumer.startMessage();
		List<ColumnDescriptor> columns = messageType.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			String value = record.get(i);
			if (value.length() > 0) {
				recordConsumer.startField(columns.get(i).getPath()[0], i);
				switch (columns.get(i).getType()) {
					case BOOLEAN:
						recordConsumer.addBoolean(Boolean.parseBoolean(value));
						break;
					case FLOAT:
						recordConsumer.addFloat(Float.parseFloat(value));
						break;
					case DOUBLE:
						recordConsumer.addDouble(Double.parseDouble(value));
						break;
					case INT32:
						recordConsumer.addInteger(Integer.parseInt(value));
						break;
					case INT64:
						recordConsumer.addLong(Long.parseLong(value));
						break;
					case BINARY:
						recordConsumer.addBinary(Binary.fromByteArray(value.getBytes(Charset.defaultCharset())));
						break;
					default:
						throw new ParquetEncodingException("Unsupported column type: " + columns.get(i).getType());
				}
				recordConsumer.endField(columns.get(i).getPath()[0], i);
			}
		}
		recordConsumer.endMessage();
	}
}
